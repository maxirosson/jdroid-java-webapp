package com.jdroid.javaweb.firebase.fcm;

import com.jdroid.java.collections.Lists;
import com.jdroid.java.http.BasicHttpResponseValidator;
import com.jdroid.java.http.DefaultServer;
import com.jdroid.java.http.HttpService;
import com.jdroid.java.http.HttpServiceProcessor;
import com.jdroid.java.http.MimeType;
import com.jdroid.java.http.Server;
import com.jdroid.java.http.post.BodyEnclosingHttpService;
import com.jdroid.java.marshaller.MarshallerProvider;
import com.jdroid.javaweb.api.ServerApiService;

import java.util.List;

public class FcmApiService extends ServerApiService {

	static {
		MarshallerProvider.get().addMarshaller(FcmMessage.class, new FcmMessageMarshaller());
	}

	public FcmResponse sendMessage(FcmMessage fcmMessage, String googleServerApiKey) {
		BodyEnclosingHttpService httpService = newPostService("send");
		httpService.addHeader(HttpService.CONTENT_TYPE_HEADER, MimeType.JSON);
		httpService.addHeader("Authorization", "key=" + googleServerApiKey);
		httpService.setSsl(true);
		marshall(httpService, fcmMessage);
		return httpService.execute(new FcmResponseParser());
	}

	@Override
	protected Server getServer() {
		return new DefaultServer("fcm", "fcm.googleapis.com/fcm", true);
	}

	@Override
	protected List<HttpServiceProcessor> getHttpServiceProcessors() {
		return Lists.<HttpServiceProcessor>newArrayList(BasicHttpResponseValidator.get());
	}
}
