package net.springboot.javaExample.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
	
	@GetMapping("/")
	private String showStudentForm() {
		return "page";
	}
	
}
