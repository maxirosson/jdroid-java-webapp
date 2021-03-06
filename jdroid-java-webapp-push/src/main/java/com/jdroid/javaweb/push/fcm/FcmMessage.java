package com.jdroid.javaweb.push.fcm;

import com.jdroid.java.collections.Lists;
import com.jdroid.java.collections.Maps;
import com.jdroid.java.utils.StringUtils;
import com.jdroid.javaweb.push.DeviceType;
import com.jdroid.javaweb.push.PushMessage;

import java.util.List;
import java.util.Map;

public class FcmMessage implements PushMessage {

	private String googleServerApiKey;

	// Required. This parameter specifies the recipient of a message. The value must be a registration token, notification key, or topic
	private String to;

	// This parameter specifies a list of devices (registration tokens, or IDs) receiving a multicast message.
	// It must contain at least 1 and at most 1000 registration tokens.
	// Use this parameter only for multicast messaging, not for single recipients.
	// Multicast messages (sending to more than 1 registration tokens) are allowed using HTTP JSON format only.
	private List<String> registrationIds;

	// Optional. This parameter identifies a group of messages (e.g., with collapse_key: "Updates Available") that can be collapsed,
	// so that only the last message gets sent when delivery can be resumed.
	// This is intended to avoid sending too many of the same messages when the device comes back online or becomes active.
	// Note that there is no guarantee of the order in which messages get sent.
	// Note: A maximum of 4 different collapse keys is allowed at any given time.
	// This means a FCM connection server can simultaneously store 4 different send-to-sync messages per client app.
	// If you exceed this number, there is no guarantee which 4 collapse keys the FCM connection server will keep.
	private String collapseKey;

	// Sets the priority of the message. Valid values are "normal" and "high." On iOS, these correspond to APNs priority 5 and 10.
	// By default, messages are sent with normal priority. Normal priority optimizes the client app's battery consumption,
	// and should be used unless immediate delivery is required. For messages with normal priority, the app may receive the message with unspecified delay.
	// When a message is sent with high priority, it is sent immediately, and the app can wake a sleeping device and open a network connection to your server.
	private FcmMessagePriority priority = FcmMessagePriority.NORMAL;

	// Optional. This parameter specifies how long (in seconds) the message should be kept in FCM storage if the device is offline.
	// The maximum time to live supported is 4 weeks. The default value is 4 weeks.
	private Integer timeToLive;

	// Optional. This parameter specifies the custom key-value pairs of the message's payload.
	// For example, with data:{"score":"3x1"}:
	// - On Android, this would result in an intent extra named score with the string value 3x1.
	// - On iOS, if the message is sent via APNS, it represents the custom data fields.
	//   If it is sent via FCM connection server, it would be represented as key value dictionary in
	//   AppDelegate application:didReceiveRemoteNotification:.
	// The key should not be a reserved word ("from" or any word starting with "google" or "gcm").
	// Values in string types are recommended. You have to convert values in objects or other non-string
	// data types (e.g., integers or booleans) to string.
	private Map<String, String> data = Maps.INSTANCE.newHashMap();

	public FcmMessage() {
		// Do nothing
	}

	public FcmMessage(String messageKey) {
		this("messageKey", messageKey);
	}

	public FcmMessage(String messageKeyExtraName, String messageKey) {
		addParameter(messageKeyExtraName, messageKey);
	}

	@Override
	public DeviceType getDeviceType() {
		return DeviceType.ANDROID;
	}

	@Override
	public void addParameter(String key, Object value) {
		if (StringUtils.isNotEmpty(key) && value != null) {
			data.put(key, value.toString());
		}
	}
	
	@Override
	public Map<String, String> getParameters() {
		return data;
	}
	
	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public List<String> getRegistrationIds() {
		return registrationIds;
	}

	public void addRegistrationId(String registrationId) {
		if (registrationId != null) {
			if (registrationIds == null) {
				registrationIds = Lists.INSTANCE.newArrayList();
			}
			registrationIds.add(registrationId);
		}
	}

	public void setRegistrationIds(List<String> registrationIds) {
		this.registrationIds = registrationIds;
	}

	public String getCollapseKey() {
		return collapseKey;
	}

	public void setCollapseKey(String collapseKey) {
		this.collapseKey = collapseKey;
	}

	public FcmMessagePriority getPriority() {
		return priority;
	}

	public void setPriority(FcmMessagePriority priority) {
		this.priority = priority;
	}

	public void markAsHighPriority() {
		this.priority = FcmMessagePriority.HIGH;
	}

	public Integer getTimeToLive() {
		return timeToLive;
	}

	public void setTimeToLive(Integer timeToLive) {
		this.timeToLive = timeToLive;
	}

	public String getGoogleServerApiKey() {
		return googleServerApiKey;
	}

	public void setGoogleServerApiKey(String googleServerApiKey) {
		this.googleServerApiKey = googleServerApiKey;
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("FcmMessage{");
		sb.append("googleServerApiKey='").append(googleServerApiKey).append('\'');
		sb.append(", to='").append(to).append('\'');
		sb.append(", registrationIds=").append(registrationIds);
		sb.append(", collapseKey='").append(collapseKey).append('\'');
		sb.append(", priority=").append(priority);
		sb.append(", timeToLive=").append(timeToLive);
		sb.append(", data=").append(data);
		sb.append('}');
		return sb.toString();
	}
}
