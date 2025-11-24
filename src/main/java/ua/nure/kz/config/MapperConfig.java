package ua.nure.kz.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ua.nure.kz.mapper.UserMapper;
import ua.nure.kz.mapper.GroupMapper;

@Configuration
public class MapperConfig {
    @Bean
    public UserMapper userMapper() {
        return new UserMapper();
    }

    @Bean
    public GroupMapper groupMapper() {
        return new GroupMapper();
    }
}
