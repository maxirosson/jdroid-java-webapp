package com.jdroid.javaweb.firebase.fcm;

public interface FcmSenderListener {

	void onSuccessfulSend(MessageSendingResponse messageSendingResponse);

	void onErrorSend(String errorCode);
}
