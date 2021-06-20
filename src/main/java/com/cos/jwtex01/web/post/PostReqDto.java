package com.cos.jwtex01.web.post;

import com.cos.jwtex01.domain.post.Post;
import com.cos.jwtex01.domain.user.User;

import lombok.Data;

@Data
public class PostReqDto {
    private String title;
    private String content;
    
	public Post toEntity(User user) {
		return Post.builder()
				.title(title)
				.content(content)
				.user(user)
				.build();
	}
}
