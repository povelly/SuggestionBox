package com.sorbonne.safetyline.config;

import com.sorbonne.safetyline.interceptor.SessionInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer
{
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("http://localhost:4200").allowCredentials(true);
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry){
		registry.addInterceptor(new SessionInterceptor())
				.addPathPatterns("/accounts","/account", "/accoundDelete",
						"/suggestions", "/suggestion","/createStrawpoll", "/account/{userId}",
						"/strawpolls");
	}
}
