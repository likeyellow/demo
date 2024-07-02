package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.example.demo.controller.LocalDateFormatter;

@Configuration // 스프링 빈으로 등록
public class CustomServletConfig implements WebMvcConfigurer {

	public void addFormatters(FormatterRegistry registry) {
		
		registry.addFormatter(new LocalDateFormatter());
	}
}
