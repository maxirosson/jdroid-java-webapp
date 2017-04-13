package com.jdroid.javaweb.sample.api.admin.controller;

import com.jdroid.java.repository.PairRepository;
import com.jdroid.javaweb.api.AdminController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class ServerAdminController extends AdminController {
	
	@Autowired
	private PairRepository configRepository;
	
	@Override
	protected PairRepository getConfigRepository() {
		return configRepository;
	}
}
