package jian.springcafe;

import jian.springcafe.repository.MemoryUserRepository;
import jian.springcafe.repository.UserRepository;
import jian.springcafe.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
    @Bean
    public UserService userService(){
        return new UserService(userRepository());
    }

    @Bean
    public UserRepository userRepository(){
        return new MemoryUserRepository();
    }
}
