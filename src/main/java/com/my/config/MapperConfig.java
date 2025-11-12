package com.my.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.my.mapper.UserMapper;

@Configuration
public class MapperConfig {
	
	@Bean
	public UserMapper userMapper() {
		return new UserMapper();
	}

}
