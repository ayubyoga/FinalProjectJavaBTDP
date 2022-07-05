package com.yoga.bus;

import org.springframework.web.bind.annotation.RequestMapping;

public class HomeController {
	@RequestMapping("/")
	public String landing() {
		return "redirect:/swagger-ui.html";
	}
}
