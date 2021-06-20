package com.cos.jwtex01.web;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.jwtex01.config.auth.LoginUser;
import com.cos.jwtex01.config.auth.Principal;
import com.cos.jwtex01.domain.post.Post;
import com.cos.jwtex01.domain.post.PostRepository;
import com.cos.jwtex01.web.post.PostReqDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class PostController {

	private final PostRepository postRepository;
	
	@GetMapping("/post")
	public List<Post> findAll() {
		return postRepository.findAll();
	}
	
	@GetMapping("/post/{id}")
	public Post findById(@PathVariable Integer id) {
		return postRepository.findById(id).get();
	}
	
	@PostMapping("/post")
	public Post save(@RequestBody PostReqDto postReqDto, @LoginUser Principal principal) {
		
		return postRepository.save(postReqDto.toEntity(principal.getUser()));
	}
}
