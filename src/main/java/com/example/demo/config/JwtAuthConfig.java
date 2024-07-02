package com.example.demo.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthConfig {

	private final AuthenticationManager authenticationManager;
	
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
	
	// Authentication 객체 만들어서 리턴 => 의존 : AuthenticationManager
	// 인증 요청시에 실행되는 함수 => /login
	
	// Tip: 인증 프로바이더의 디폴트 서비스는 UserDetailsService 타입
	// Tip: 인증 프로바이더의 디폴트 암호화 방식은 BCryptPasswordEncoder
	// 결론은 인증 프로바이더에게 알려줄 필요가 없음.
	Authentication authentication = null; 
			//authenticationManager.authenticate(authenticationToken);
	
	return authentication;
	}
}
