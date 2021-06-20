package com.cos.jwtex01.web.user;


import com.cos.jwtex01.domain.user.User;

import lombok.Data;

@Data
public class JoinReqDto {
	private String username;
	private String password;
	
	public User toEntity() {
		return User.builder()
				.username(username)
				.password(password)
				.role("ROLE_USER")
				.build();
	}
}
