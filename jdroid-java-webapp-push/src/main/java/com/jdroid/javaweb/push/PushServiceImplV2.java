package com.jdroid.javaweb.push;

import com.jdroid.java.concurrent.ExecutorUtils;
import com.jdroid.java.date.DateUtils;
import com.jdroid.java.utils.LoggerUtils;
import com.jdroid.javaweb.application.Application;
import com.jdroid.javaweb.config.CoreConfigParameter;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map.Entry;

@Service
public class PushServiceImplV2 implements PushService {
	
	private static final Logger LOGGER = LoggerUtils.getLogger(PushServiceImplV2.class);
	
	@Autowired
	private DeviceRepository deviceRepository;

	@Autowired(required = false)
	private PushServiceListener pushServiceListener;

	@Override
	public void addDevice(Device device, Boolean updateLastActiveTimestamp) {
		device.setId(generateId(device.getDeviceType(), device.getInstanceId()));
		
		Device deviceToUpdate = deviceRepository.get(device.getId());
		if (deviceToUpdate != null) {
			if (isDeviceUpdateRequired(deviceToUpdate, device)) {
				deviceToUpdate.setLastActiveTimestamp(updateLastActiveTimestamp ? DateUtils.nowMillis() : device.getLastActiveTimestamp());
				deviceToUpdate.setRegistrationToken(device.getRegistrationToken());
				deviceToUpdate.setDeviceGroupId(device.getDeviceGroupId());
				deviceToUpdate.setAppVersionCode(device.getAppVersionCode());
				deviceToUpdate.setDeviceOsVersion(device.getDeviceOsVersion());
				deviceToUpdate.setAcceptLanguage(device.getAcceptLanguage());
				deviceToUpdate.setExtras(device.getExtras());
				deviceRepository.update(deviceToUpdate);
				if (pushServiceListener != null) {
					pushServiceListener.onUpdateDevice(deviceToUpdate.getInstanceId(), deviceToUpdate.getDeviceType());
				}
				
			}
		} else {
			Long now = DateUtils.nowMillis();
			device.setCreationTimestamp(now);
			device.setLastActiveTimestamp(now);
			deviceRepository.add(device);
			if (pushServiceListener != null) {
				pushServiceListener.onAddDevice(device.getInstanceId(), device.getDeviceType());
			}
		}
	}
	
	protected Boolean isDeviceUpdateRequired(Device oldDevice, Device newDevice) {
		newDevice.setLastActiveTimestamp(oldDevice.getLastActiveTimestamp());
		newDevice.setId(oldDevice.getId());
		return !oldDevice.equals(newDevice) || DateUtils.nowMillis() - oldDevice.getLastActiveTimestamp() > Application.get().getRemoteConfigLoader().getLong(CoreConfigParameter.DEVICE_UPDATE_REQUIRED_DURATION);
	}
	
	private String generateId(DeviceType deviceType, String instanceId) {
		return deviceType.getUserAgent() + "-" + instanceId;
	}

	@Override
	public void removeDevice(String instanceId, DeviceType deviceType) {
		Device deviceToRemove = deviceRepository.get(generateId(deviceType, instanceId));
		if (deviceToRemove != null) {
			deviceRepository.remove(deviceToRemove);
			if (pushServiceListener != null) {
				pushServiceListener.onRemoveDevice(deviceToRemove.getInstanceId(), deviceToRemove.getDeviceType());
			}
		}
	}

	@Override
	public void send(final PushMessage pushMessage) {
		ExecutorUtils.execute(new PushProcessor(this, pushMessage));
	}

	// TODO By Google recommendation, we should remove all the tokens that weren't recently updated
	@Override
	public void processResponse(PushResponse pushResponse) {
		for (String each : pushResponse.getRegistrationTokensToRemove()) {
			Device deviceToRemove = deviceRepository.findByRegistrationToken(each, pushResponse.getDeviceType());
			if (deviceToRemove != null) {
				deviceRepository.remove(deviceToRemove);
				if (pushServiceListener != null) {
					pushServiceListener.onRemoveDevice(deviceToRemove.getInstanceId(), deviceToRemove.getDeviceType());
				}
			}
		}

		for (Entry<String, String> entry : pushResponse.getRegistrationTokensToReplace().entrySet()) {
			Device deviceToUpdate = deviceRepository.findByRegistrationToken(entry.getKey(), pushResponse.getDeviceType());
			if (deviceToUpdate != null) {
				deviceToUpdate.setRegistrationToken(entry.getValue());
				deviceRepository.update(deviceToUpdate);
				if (pushServiceListener != null) {
					pushServiceListener.onUpdateDevice(deviceToUpdate.getInstanceId(), deviceToUpdate.getDeviceType());
				}
			}
		}
	}

	private class PushProcessor implements Runnable {
		
		private PushService pushService;
		private PushMessage pushMessage;
		
		public PushProcessor(PushService pushService, PushMessage pushMessage) {
			this.pushService = pushService;
			this.pushMessage = pushMessage;
		}
		
		@Override
		public void run() {
			PushResponse pushResponse = pushMessage.getDeviceType().send(pushMessage);
			pushService.processResponse(pushResponse);
		}
	}
}
