package com.jdroid.javaweb.application;

import com.jdroid.java.collections.Lists;
import com.jdroid.java.collections.Maps;
import com.jdroid.java.context.GitContext;
import com.jdroid.java.domain.Entity;
import com.jdroid.javaweb.context.AbstractSecurityContext;
import com.jdroid.javaweb.context.AppContext;
import com.jdroid.javaweb.context.SecurityContextHolder;
import com.jdroid.javaweb.context.ServerGitContext;

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
	
	private AppContext appContext;
	private GitContext gitContext;
	private SecurityContextHolder<T> securityContextHolder;
	private ApplicationContext springApplicationContext;

	private Map<String, AppModule> appModulesMap = Maps.newLinkedHashMap();

	public Application() {
		INSTANCE = this;

		appContext = createAppContext();
		gitContext = createGitContext();

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

	protected AppContext createAppContext() {
		return new AppContext();
	}

	protected GitContext createGitContext() {
		return new ServerGitContext();
	}

	public AppContext getAppContext() {
		return appContext;
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
	
	/**
	 * @param beanName The bean name
	 * @return The spring bean with the bean name
	 */
	public Object getBean(String beanName) {
		return springApplicationContext.getBean(beanName);
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
	
	public GitContext getGitContext() {
		return gitContext;
	}
}
