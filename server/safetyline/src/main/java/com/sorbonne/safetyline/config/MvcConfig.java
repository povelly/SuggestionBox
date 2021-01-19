package com.sorbonne.safetyline.config;

import com.sorbonne.safetyline.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer
{
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowCredentials(true);
	}
	@Override
	public void addInterceptors(InterceptorRegistry registry){
		registry.addInterceptor(new LoginInterceptor())
				.addPathPatterns("/accounts","/account", "/accoundDelete",
						"/suggestions", "/suggestion","/createStrawpoll", "/account/{userId}");
	}
}
