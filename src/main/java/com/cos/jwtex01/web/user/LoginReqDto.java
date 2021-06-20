package com.cos.jwtex01.web.user;

import lombok.Data;

@Data
public class LoginReqDto {
	private String username;
	private String password;
}
