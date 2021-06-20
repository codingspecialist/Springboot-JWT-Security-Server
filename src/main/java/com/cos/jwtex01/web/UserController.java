package com.cos.jwtex01.web;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.jwtex01.domain.user.User;
import com.cos.jwtex01.domain.user.UserRepository;
import com.cos.jwtex01.web.user.JoinReqDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {
	
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	@PostMapping("/join")
	public User join(@RequestBody JoinReqDto joinReqDto) {
		joinReqDto.setPassword(bCryptPasswordEncoder.encode(joinReqDto.getPassword()));
		return userRepository.save(joinReqDto.toEntity());
	}
}











