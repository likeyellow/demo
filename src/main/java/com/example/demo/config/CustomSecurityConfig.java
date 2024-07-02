package com.example.demo.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2LoginConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.demo.security.JwtAuthenticationFilter;
import com.example.demo.security.OAuthSuccessHandler;
import com.example.demo.security.OAuthUserServiceImpl;
import com.example.demo.security.RedirectUrlCookieFilter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Configuration // IoC 빈(bean)을 등록
@EnableWebSecurity // 시큐리티 활성화 -> 기본 스프링 필터체인에 등록
@Slf4j
public class CustomSecurityConfig {

	//@Autowired
	//private JwtAuthenticationFilter jwtAuthenticationFilter;

	@Autowired
	private OAuthUserServiceImpl oAuthUserService;

	@Autowired
	private OAuthSuccessHandler oAuthSuccessHandler; // Success Handler 추가

	@Autowired
	private RedirectUrlCookieFilter redirectUrlFilter;
	
	@Autowired
	private CorsConfig corsConfig;
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		
		// http 시큐리티 빌더
		//	.cors().disable() // WebMvcConfig에서 이미 설정했으므로 기본 cors 설정
		//	.and()
		http
			.cors(httpSecurityCorsConfigurer -> {
				httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource());
			});
		http
			.csrf().disable(); // csrf는 현재 사용하지 않으므로 disable
		http
			.sessionManagement(sessionConfig -> 
								sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http
			.csrf(config -> config.disable());
		
		// http
		//	.httpBasic().disable() // token을 사용하므로 basic 인증 disable
		//	.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // session 기반이 아님을 선언
			//.and()
			//.apply(new CustomFilterOrder()) // 커스텀 필터 등록
			//.and()
/*		http
			.authorizeRequests() 						  
			.antMatchers("/","/home/**", "/board/**", "/auth/**", "/oauth2/**").permitAll() // -- /와 /auth/** 경로는 인증 안 해도 됨.
			.anyRequest().authenticated() // -- /와 /auth/** 이외의 모든 경로는 인증해야 됨.
			

			
			.and()
			.oauth2Login()
			.redirectionEndpoint()
			.baseUri("/oauth2/callback/*")
			.and()
			.authorizationEndpoint()
			.baseUri("/auth/authorize") // OAuth 2.0 흐름 시작을 위한 엔드포인트 추가
			.and()
			.userInfoEndpoint()
			.userService(oAuthUserService)
			.and()
			.successHandler(oAuthSuccessHandler)
			.and()
			.exceptionHandling()
				.authenticationEntryPoint(new Http403ForbiddenEntryPoint()); 
	*/	
		return http.build();
		
		// filter 등록
		// 매 요청마다
		// CorsFilter 실행한 후에
		// jwtAuthenticationFilter 실행한다.
		// http.addFilterAfter(jwtAuthenticationFilter, CorsFilter.class);
	 }
	
/*
	public class CustomFilterOrder extends AbstractHttpConfigurer<CustomFilterOrder, HttpSecurity> {
		
		@Override
		public void configure(HttpSecurity http) throws Exception {
			AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
			
			http
			    .addFilter(corsConfig.corsFilter());
			    .addFilter(new JwtAuthenticationFilter(authenticationManager));
			    .addFilter(new JwtAuthorizationFilter(authenticationManager, userRepository));				
		}
	}
*/	
	@Bean
	public PasswordEncoder passwordEncoder() {
		
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
/*	
		registry.addMapping("/**")
				.allowedOrigins("*")
				.allowedMethods("HEAD", "GET", "POST", "PUT", "DELETE", "OPTIONS")
				.maxAge(300)
				.allowedHeaders("Authorization", "Cache-Control", "Content-Type");
	}
	//	addMapping - CORS를 적용할 url의 패턴을 정의 (/** 로 모든 패턴을 가능하게 함)
	//	allowedOrigins - 허용할 origin을 정의 (* 로 모든 origin을 허용, 여러개도 지정가능)
	//	allowedMethods - HTTP Method를 지정 (* 로 모든 Method를 허용)
	//	maxAge - 원하는 시간만큼 request를 cashing함
*/
		System.out.println("corsFilter called......");
		
		CorsConfiguration configuration = new CorsConfiguration();
		
		configuration.setAllowedOriginPatterns(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE", "OPTIONS"));
		configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
		configuration.setAllowCredentials(true);
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;		
	}
	
}
