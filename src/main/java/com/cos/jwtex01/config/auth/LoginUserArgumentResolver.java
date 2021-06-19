package com.cos.jwtex01.config.auth;

import javax.servlet.http.HttpSession;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver{

	private final HttpSession httpSession;
	
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		boolean isPrincipalAnnotation = parameter.getParameterAnnotation(LoginUser.class) != null;
		boolean isPrincipalClass = Principal.class.equals(parameter.getParameterType());
		
		return isPrincipalAnnotation && isPrincipalClass;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		return httpSession.getAttribute("principal");
	}
}
