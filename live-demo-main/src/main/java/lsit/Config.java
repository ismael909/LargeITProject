package lsit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.http.HttpMethod;

@Configuration
@EnableWebSecurity
public class Config{

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(c -> c.disable())
            .oauth2Login(withDefaults())
            .authorizeHttpRequests(a -> a
                .requestMatchers("/user").authenticated()
                .requestMatchers(HttpMethod.POST, "/clothes").authenticated()
                .anyRequest().permitAll()
            )
            ;

        return http.build();
    }

}

    
