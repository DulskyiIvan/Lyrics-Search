package edu.geekhub.example.service;

import edu.geekhub.example.authentication.user.model.User;
import edu.geekhub.example.authentication.user.service.UserDetailsServiceImp;
import edu.geekhub.example.service.role.model.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.UUID;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@Configuration
public class MockConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetailsService userDetailsService = mock(UserDetailsServiceImp.class);
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setFirstName("User");
        user.setLastName("User");
        user.setUsername("username");
        user.setEmail("example-user@com.ua");
        user.setPassword("1234");
        user.setRole(new Role().setTitle("USER").setId(1));

        User admin = new User();
        admin.setId(UUID.randomUUID());
        admin.setFirstName("Admin");
        admin.setLastName("Admin");
        admin.setUsername("adminname");
        admin.setEmail("example-admin@com.ua");
        admin.setPassword("1234");
        admin.setRole(new Role().setTitle("ADMIN").setId(2));

        doReturn(user).when(userDetailsService).loadUserByUsername("username");
        doReturn(admin).when(userDetailsService).loadUserByUsername("adminname");
        return userDetailsService;
    }
}
