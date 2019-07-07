package com.jdroid.javaweb.push;

public interface PushMessageSender {
	
	PushResponse send(PushMessage pushMessage);
	
}