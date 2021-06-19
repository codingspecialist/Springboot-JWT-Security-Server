package com.cos.jwtex01.controller.auth;

import lombok.Data;

@Data
public class LoginReqDto {
	private String username;
	private String password;
}
