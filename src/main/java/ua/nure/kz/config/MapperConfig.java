package ua.nure.kz.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ua.nure.kz.mapper.UserMapper;

@Configuration
public class MapperConfig {
	
	@Bean
	public UserMapper userMapper() {
		return new UserMapper();
	}

}
