package com.jdroid.javaweb.push.fcm;

import com.jdroid.java.collections.Lists;
import com.jdroid.java.concurrent.ExecutorUtils;
import com.jdroid.java.exception.UnexpectedException;
import com.jdroid.java.http.exception.ConnectionException;
import com.jdroid.java.utils.LoggerUtils;
import com.jdroid.java.utils.RandomUtils;
import com.jdroid.java.utils.StringUtils;
import com.jdroid.javaweb.application.Application;
import com.jdroid.javaweb.config.CoreConfigParameter;
import com.jdroid.javaweb.push.DeviceType;
import com.jdroid.javaweb.push.PushMessage;
import com.jdroid.javaweb.push.PushMessageSender;
import com.jdroid.javaweb.push.PushResponse;

import org.slf4j.Logger;

import java.util.concurrent.TimeUnit;


public class FcmSender implements PushMessageSender {
	
	private static final Logger LOGGER = LoggerUtils.getLogger(FcmSender.class);

	// Initial delay before first retry
	private static final int BACKOFF_INITIAL_DELAY = 1000;

	// Maximum delay before a retry.
	protected static final int MAX_BACKOFF_DELAY = 1024000;
	
	private final static PushMessageSender INSTANCE = new FcmSender();

	private FcmApiService fcmApiService = new FcmApiService();
	
	private FcmSender() {
	}
	
	public static PushMessageSender get() {
		return INSTANCE;
	}

	@Override
	public PushResponse send(PushMessage pushMessage) {
		return send(pushMessage, 10);
	}

	private PushResponse send(PushMessage pushMessage, int retries) {

		FcmMessage fcmMessage = (FcmMessage)pushMessage;

		int attempt = 0;
		PushResponse pushResponse = null;
		int backoff = BACKOFF_INITIAL_DELAY;
		boolean tryAgain = true;
		while (tryAgain) {
			attempt++;

			LOGGER.debug("Attempt #" + attempt + " to send message " + pushMessage);
			try {
				pushResponse = sendNoRetry(fcmMessage);
				tryAgain = (pushResponse == null || !pushResponse.getRegistrationTokensToRetry().isEmpty()) && attempt <= retries;
			} catch (ConnectionException e) {
				LOGGER.error("ConnectionException when sending a push", e);
				tryAgain = true;
			}

			if (tryAgain) {
				if (pushResponse != null && !pushResponse.getRegistrationTokensToRetry().isEmpty()) {
					fcmMessage.setRegistrationIds(pushResponse.getRegistrationTokensToRetry());
				}
				int sleepTime = backoff / 2 + RandomUtils.getInt(backoff);
				LOGGER.debug("Next attempt on " + sleepTime / 1000 + " seconds");
				ExecutorUtils.sleep(sleepTime, TimeUnit.MILLISECONDS);
				if (2 * backoff < MAX_BACKOFF_DELAY) {
					backoff *= 2;
				}
			}
		};
		if (pushResponse == null) {
			throw new UnexpectedException("Could not send message after " + attempt + " attempts");
		}
		return pushResponse;
	}
	

	private PushResponse sendNoRetry(FcmMessage fcmMessage) {

		String googleServerApiKey = StringUtils.isNotBlank(fcmMessage.getGoogleServerApiKey()) ? fcmMessage.getGoogleServerApiKey() : Application.get().getRemoteConfigLoader().getString(CoreConfigParameter.GOOGLE_SERVER_API_KEY);
		FcmResponse fcmResponse = fcmApiService.sendMessage(fcmMessage, googleServerApiKey);

		PushResponse pushResponse = new PushResponse(DeviceType.ANDROID);
		if (!fcmResponse.isOk()) {
			for (int i = 0; i < fcmResponse.getResults().size(); i++) {
				FcmResult each = fcmResponse.getResults().get(i);
				if (each.getMessageId() != null) {
					if (each.getRegistrationId() != null) {
						// Replace the original ID with the new value (canonical ID) in your server database.
						// Note that the original ID is not part of the result, so you need to obtain it from the list of registration_ids passed in
						// the request (using the same index).
						String registrationIdToReplace = fcmMessage.getRegistrationIds().get(i);
						pushResponse.addRegistrationTokenToReplace(registrationIdToReplace, each.getRegistrationId());
						LOGGER.info("Registration id [" + registrationIdToReplace + "] to be replaced by " + each.getRegistrationId());
					}
				} else {
					if ("Unavailable".equals(each.getError())) {
						LOGGER.info("Error [" + each.getError() + "] when sending FCM message/s");
						// The server couldn't process the request in time. Retry the same request, but you must:
						// 	Honor the Retry-After header if it is included in the response from the FCM Connection Server.
						// 	Implement exponential back-off in your retry mechanism. (e.g. if you waited one second before the first retry,
						// 	wait at least two second before the next one, then 4 seconds and so on). If you're sending multiple messages,
						// 	delay each one independently by an additional random amount to avoid issuing a new request for all messages at the same time.
						// Senders that cause problems risk being blacklisted.
						if (fcmMessage.getTo() != null) {
							return null;
						} else {
							pushResponse.addRegistrationTokenToRetry(fcmMessage.getRegistrationIds().get(i));
							return pushResponse;
						}
					} else if ("NotRegistered".equals(each.getError())) {
						LOGGER.info("Error [" + each.getError() + "] when sending FCM message/s");
						// Remove the registration ID from the server database because the application was uninstalled from the device,
						// or the client app isn't configured to receive messages.
						removeRegistrationId(fcmMessage, pushResponse, i);
					} else {
						// there is something wrong in the registration token passed in the request; it is probably a non-recoverable error
						// that will require removing the registration from the server database
						LOGGER.error("Error [" + each.getError() + "] when sending FCM message/s");
						removeRegistrationId(fcmMessage, pushResponse, i);
					}
				}
			}
		} else {
			StringBuilder builder = new StringBuilder();
			builder.append("Fcm message sent successfully. ");
			builder.append(fcmMessage.toString());
			if (!Lists.isNullOrEmpty(fcmResponse.getResults())) {
				builder.append(". Message id: ");
				builder.append(fcmResponse.getResults().get(0).getMessageId());
			}
			LOGGER.info(builder.toString());
		}
		return pushResponse;
	}

	private void removeRegistrationId(FcmMessage fcmMessage, PushResponse pushResponse, int index) {
		// you should remove the registration ID from your server database because the application was uninstalled from the device,
		// or the client app isn't configured to receive messages.
		String registrationIdToRemove = null;
		if (fcmMessage.getTo() != null) {
			registrationIdToRemove = fcmMessage.getTo();
		} else {
			registrationIdToRemove = fcmMessage.getRegistrationIds().get(index);
		}
		LOGGER.info("Registration id [" + registrationIdToRemove + "] to be removed");
		pushResponse.addRegistrationTokenToRemove(registrationIdToRemove);
	}
}
