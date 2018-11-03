package com.jdroid.javaweb.sample.api.controller;

import com.google.firebase.messaging.AndroidConfig;
import com.google.firebase.messaging.Message;
import com.jdroid.java.date.DateUtils;
import com.jdroid.java.http.MimeType;
import com.jdroid.java.utils.StringUtils;
import com.jdroid.javaweb.api.AbstractController;
import com.jdroid.javaweb.firebase.fcm.Device;
import com.jdroid.javaweb.firebase.fcm.DeviceParser;
import com.jdroid.javaweb.firebase.fcm.DeviceRepository;
import com.jdroid.javaweb.firebase.fcm.DeviceType;
import com.jdroid.javaweb.firebase.fcm.PushService;
import com.jdroid.javaweb.firebase.fcm.api.DeviceHeaders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/fcm")
public class FcmController extends AbstractController {
	
	@Autowired
	private PushService pushService;

	@Autowired
	private DeviceRepository deviceRepository;
	
	@RequestMapping(value = "/send", method = RequestMethod.GET)
	public void send(@RequestParam(required = false) String registrationToken,
					 @RequestParam(required = false) String topic,
				 	 @RequestParam String messageKeyExtraName,
					 @RequestParam String messageKey,
					 @RequestParam(required = false) String collapseKey,
					 @RequestParam(required = false) String highPriority,
					 @RequestParam(required = false) Integer timeToLive,
					 @RequestParam(required = false) String timestampEnabled,
					 @RequestParam(required = false) String params) {

		Message.Builder builder = Message.builder();
		AndroidConfig.Builder androidConfigBuilder = AndroidConfig.builder();

		builder.putData(messageKeyExtraName, messageKey);

		if (StringUtils.isNotEmpty(registrationToken)) {
			builder.setToken(registrationToken);
		}

		if (StringUtils.isNotEmpty(topic)) {
			builder.setTopic(topic);
		}

		androidConfigBuilder.setCollapseKey(StringUtils.isNotEmpty(collapseKey) ? collapseKey : null);
		if (highPriority != null && highPriority.equalsIgnoreCase("true")) {
			androidConfigBuilder.setPriority(AndroidConfig.Priority.HIGH);
		}
		if (timeToLive != null) {
			androidConfigBuilder.setTtl(timeToLive);
		}
		if (timestampEnabled != null && timestampEnabled.equalsIgnoreCase("true")) {
			builder.putData("timestamp", "" + DateUtils.nowMillis());
		}

		if (params != null) {
			for (String param : StringUtils.splitWithCommaSeparator(params.replace("[", "").replace("]", ""))) {
				String[] vec = param.split("\\|");
				builder.putData(vec[0], vec[1]);
			}
		}

		builder.setAndroidConfig(androidConfigBuilder.build());
		pushService.send(builder.build());
	}

	@RequestMapping(value = "/device", method = RequestMethod.GET, produces = MimeType.JSON_UTF8)
	@ResponseBody
	public String getAllDevices() {
		List<Device> devices = deviceRepository.getAll();
//		InstanceIdApiService instanceIdApiService = new InstanceIdApiService();
//		for(Device device : devices) {
//			instanceIdApiService.verify(device.getRegistrationToken(), Application.get().getAppContext().getGoogleServerApiKey());
//		}
		return marshall(devices);
	}

	@RequestMapping(value = "/device", method = RequestMethod.POST)
	public void addDevice(@RequestHeader(value = DeviceHeaders.INSTANCE_ID_HEADER) String instanceId, @RequestHeader(value = "User-Agent") String userAgent,
						  @RequestHeader(value="Accept-Language") String acceptLanguage, @RequestBody String deviceJSON, @RequestParam Boolean updateLastActiveTimestamp) {
		DeviceParser parser = new DeviceParser(instanceId, userAgent, acceptLanguage);
		Device device = (Device)parser.parse(deviceJSON);
		pushService.addDevice(device, updateLastActiveTimestamp);
	}

	@RequestMapping(value = "/device", method = RequestMethod.DELETE)
	public void removeDevice(@RequestHeader(value = "User-Agent") String userAgent, @RequestHeader(value = DeviceHeaders.INSTANCE_ID_HEADER) String instanceId) {
		pushService.removeDevice(instanceId, DeviceType.find(userAgent));
	}
}