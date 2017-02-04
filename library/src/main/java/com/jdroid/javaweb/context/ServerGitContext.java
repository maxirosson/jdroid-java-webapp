package com.jdroid.javaweb.context;

import com.jdroid.java.context.GitContext;

public class ServerGitContext extends AbstractAppContext implements GitContext {

	public String getSha() {
		return getBuildConfigValue("GIT_SHA");
	}

	public String getBranch() {
		return getBuildConfigValue("GIT_BRANCH");
	}
}
