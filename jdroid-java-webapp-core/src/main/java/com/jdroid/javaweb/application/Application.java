package com.jdroid.javaweb.application;

import com.jdroid.java.collections.Lists;
import com.jdroid.java.collections.Maps;
import com.jdroid.java.domain.Entity;
import com.jdroid.javaweb.context.AbstractSecurityContext;
import com.jdroid.javaweb.context.SecurityContextHolder;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.List;
import java.util.Map;

/**
 * 
 * @param <T>
 */
public class Application<T extends Entity> implements ApplicationContextAware {
	
	private static Application<?> INSTANCE;
	
	public static Application<?> get() {
		return INSTANCE;
	}
	
	private SecurityContextHolder<T> securityContextHolder;
	private ApplicationContext springApplicationContext;

	private Map<String, AppModule> appModulesMap = Maps.newLinkedHashMap();

	public Application() {
		INSTANCE = this;

		initAppModule(appModulesMap);
		for (AppModule each : appModulesMap.values()) {
			each.onCreateApplication();
		}
	}

	protected void initAppModule(Map<String, AppModule> appModulesMap) {
		// Do nothing
	}

	public AppModule getAppModule(String appModuleName) {
		return appModulesMap.get(appModuleName);
	}

	public List<AppModule> getAppModules() {
		return Lists.newArrayList(appModulesMap.values());
	}

	public void onContextDestroyed() {
		for (AppModule each : appModulesMap.values()) {
			each.onContextDestroyed();
		}
	}

	public Class<?> getBuildConfigClass() {
		return null;
	}
	
	/**
	 * @return The {@link AbstractSecurityContext} instance
	 */
	public AbstractSecurityContext<T> getSecurityContext() {
		return securityContextHolder != null ? securityContextHolder.getContext() : null;
	}
	
	public ApplicationContext getSpringApplicationContext() {
		return springApplicationContext;
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.springApplicationContext = applicationContext;
	}
	
	/**
	 * @param securityContextHolder The {@link SecurityContextHolder} to set
	 */
	public void setSecurityContextHolder(SecurityContextHolder<T> securityContextHolder) {
		this.securityContextHolder = securityContextHolder;
	}
}
