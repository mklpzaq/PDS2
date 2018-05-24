package com.test.pds2;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {
	@RequestMapping(value="/addUser", method=RequestMethod.GET)
	public String addUser() {
		return "addUser";
	}
	
	@RequestMapping(value="/addUser", method=RequestMethod.POST)
	public String addUser(String user) {
		// service 호출 -> dao 호출
		return "redirect:/";
	}
}
