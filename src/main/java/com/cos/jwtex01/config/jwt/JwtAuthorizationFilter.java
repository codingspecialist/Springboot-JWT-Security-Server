package com.cos.jwtex01.config.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.cos.jwtex01.config.auth.LoginUser;
import com.cos.jwtex01.config.auth.Principal;
import com.cos.jwtex01.domain.user.User;
import com.cos.jwtex01.domain.user.UserRepository;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter{
	
	private final UserRepository userRepository;
	private final HttpSession session;

	
	public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository, HttpSession session) {
		super(authenticationManager);
		this.userRepository = userRepository;
		this.session = session;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String header = request.getHeader(JwtProperties.HEADER_STRING);
		if(header == null || !header.startsWith(JwtProperties.TOKEN_PREFIX)) {
			chain.doFilter(request, response);
                        return;
		}
		System.out.println("header : "+header);
		String token = request.getHeader(JwtProperties.HEADER_STRING)
				.replace(JwtProperties.TOKEN_PREFIX, "");
		
		String username = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(token)
				.getClaim("username").asString();
		
		
		if(username != null) {	
			User user = userRepository.findByUsername(username);
			
			// 1. authenticationManager.authenticate() 함수를 타게 되면 인증을 한번 더 하게 되고
			// 이때 비밀번호 검증을 하게 되는데, User 테이블에서 가져온 비밀번호 해시값으로 비교가 불가능하다.
			// 2. 그래서 강제로 세션에 저장만 한다.
			// 3. 단점은 @AuthenticationPrincipal 어노테이션을 사용하지 못하는 것이다.
			// 4. 이유는 UserDetailsService를 타지 않으면 @AuthenticationPrincipal 이 만들어지지 않는다.
			// 5. 그래서 @LoginUser 을 하나 만들려고 한다.
			// 6. 그러므로 모든 곳에서 @AuthenticationPrincipal 사용을 금지한다. @LoginUser 사용 추천!!
			
			
			Principal principalDetails = new Principal(user);
			session.setAttribute("principal", principalDetails);
			Authentication authentication =
					new UsernamePasswordAuthenticationToken(
							principalDetails.getUsername(),
							principalDetails.getPassword(), 
							principalDetails.getAuthorities());

			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
	
		chain.doFilter(request, response);
	}
	
}
