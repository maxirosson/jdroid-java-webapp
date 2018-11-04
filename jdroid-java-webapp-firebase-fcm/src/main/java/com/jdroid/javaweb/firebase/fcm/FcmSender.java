package com.jdroid.javaweb.firebase.fcm;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.jdroid.java.concurrent.ExecutorUtils;
import com.jdroid.java.utils.LoggerUtils;

import org.slf4j.Logger;

import java.util.concurrent.TimeUnit;


public class FcmSender {

	private static final Logger LOGGER = LoggerUtils.getLogger(FcmSender.class);

	private static final int RETRIES = 10;

	private final static FcmSender INSTANCE = new FcmSender();

	private FcmSender() {
	}
	
	public static FcmSender get() {
		return INSTANCE;
	}

	public void send(Message message) {
		sendRetry(message, null, 1);
	}

	public void send(Message message, FcmSenderListener listener) {
		sendRetry(message, listener, 1);
	}

	private void sendRetry(Message message, FcmSenderListener listener, int attempt) {
		LOGGER.info("Attempt #" + attempt + " to send message " + message);
		MessageSendingResponse messageSendingResponse = sendNoRetry(message);
		if (messageSendingResponse.getSuccessful()) {
			if (listener != null) {
				listener.onSuccessfulSend(messageSendingResponse);
			}
		} else {
			if (messageSendingResponse.getRetry()) {
				if (attempt <= RETRIES) {
					long backoff = (long) (Math.pow(2, attempt));
					ExecutorUtils.schedule(new Runnable() {
						@Override
						public void run() {
							sendRetry(message, listener, attempt + 1);
						}
					}, backoff, TimeUnit.SECONDS);
				} else {
					LOGGER.error("Could not send message after " + attempt + " attempts");
					if (listener != null) {
						listener.onErrorSend(messageSendingResponse.getErrorCode());
					}
				}
			} else {
				if (listener != null) {
					listener.onErrorSend(messageSendingResponse.getErrorCode());
				}
			}
		}
	}

	private MessageSendingResponse sendNoRetry(Message message) {

		// TODO
		MessageSendingResponse messageSendingResponse = new MessageSendingResponse(DeviceType.ANDROID);

		try {
			String messageId = FirebaseMessaging.getInstance().send(message);

			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("Fcm message sent successfully. ");
			stringBuilder.append(message.toString());
			stringBuilder.append(". Message id: ");
			stringBuilder.append(messageId);
			LOGGER.info(stringBuilder.toString());
		} catch(FirebaseMessagingException e) {

			LOGGER.error("Error [" + e.getErrorCode() + "] when sending FCM message. ", e);
			messageSendingResponse.setErrorCode(e.getErrorCode());

			if (e.getErrorCode().equals("invalid-argument")) {
				messageSendingResponse.setRetry(false);
			} else if (e.getErrorCode().equals("invalid-recipient")) {
				messageSendingResponse.setRetry(false);
			} else if (e.getErrorCode().equals("invalid-payload")) {
				messageSendingResponse.setRetry(false);
			} else if (e.getErrorCode().equals("invalid-data-payload-key")) {
				messageSendingResponse.setRetry(false);
			} else if (e.getErrorCode().equals("payload-size-limit-exceeded")) {
				messageSendingResponse.setRetry(false);
			} else if (e.getErrorCode().equals("invalid-options")) {
				messageSendingResponse.setRetry(false);
			} else if (e.getErrorCode().equals("invalid-registration-token")) {
				messageSendingResponse.setRetry(false);
			} else if (e.getErrorCode().equals("registration-token-not-registered")) {
				messageSendingResponse.setRetry(false);
				// TODO
				//messageSendingResponse.addRegistrationTokenToRemove(message.getToken());
			} else if (e.getErrorCode().equals("invalid-package-name")) {
				messageSendingResponse.setRetry(false);
			} else if (e.getErrorCode().equals("message-rate-exceeded")) {
				messageSendingResponse.setRetry(true);
			} else if (e.getErrorCode().equals("device-message-rate-exceeded")) {
				messageSendingResponse.setRetry(true);
			} else if (e.getErrorCode().equals("too-many-topics")) {
				messageSendingResponse.setRetry(false);
			} else if (e.getErrorCode().equals("invalid-apns-credentials")) {
				messageSendingResponse.setRetry(false);
			} else if (e.getErrorCode().equals("mismatched-credential")) {
				messageSendingResponse.setRetry(false);
			} else if (e.getErrorCode().equals("authentication-error")) {
				messageSendingResponse.setRetry(false);
			} else if (e.getErrorCode().equals("server-unavailable")) {
				messageSendingResponse.setRetry(true);
			} else if (e.getErrorCode().equals("internal-error")) {
				messageSendingResponse.setRetry(true);
			} else if (e.getErrorCode().equals("unknown-error")) {
				messageSendingResponse.setRetry(true);
			} else {
				messageSendingResponse.setRetry(false);
			}
		} catch(Exception e) {
			LOGGER.error("Error [" + e.getMessage() + "] when sending FCM message", e);
			messageSendingResponse.setErrorCode(e.getMessage());
			messageSendingResponse.setRetry(true);
		}
		return messageSendingResponse;
	}
}
