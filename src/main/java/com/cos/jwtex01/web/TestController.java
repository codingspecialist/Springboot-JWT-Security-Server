package com.cos.jwtex01.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.jwtex01.config.auth.LoginUser;
import com.cos.jwtex01.config.auth.Principal;

@RestController
public class TestController {
	
	// 유저 혹은 어드민이 접근 가능
	@GetMapping("/user")
	public String user(@LoginUser Principal principal) {
		System.out.println("principal : "+principal.getUsername());
		return "<h1>user</h1>";
	}	
}











