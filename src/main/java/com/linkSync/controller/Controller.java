package com.linkSync.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

	@RequestMapping(value = "checkAPI", method = RequestMethod.GET)
	public String checkAPI() {
		return "API working fine !!!";
	}
}
