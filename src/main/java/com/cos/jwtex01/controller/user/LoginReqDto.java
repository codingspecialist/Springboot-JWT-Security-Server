package com.cos.jwtex01.controller.user;

import lombok.Data;

@Data
public class LoginReqDto {
	private String username;
	private String password;
}
