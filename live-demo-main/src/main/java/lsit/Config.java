package lsit;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class Config {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Disable CSRF for simplicity
            .oauth2Login(oauth -> oauth
                .userInfoEndpoint(userInfo -> userInfo.userService(oauth2UserService())) // Map GitLab groups to roles
            )
            .authorizeHttpRequests(auth -> auth
                .anyRequest().authenticated() // Require authentication for all other requests
            );

        return http.build();
    }

    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2UserService() {
        return userRequest -> {
            // Delegate to the default implementation for loading user info
            DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
            OAuth2User oAuth2User = delegate.loadUser(userRequest);

            // Extract claims or groups from user attributes
            Map<String, Object> attributes = oAuth2User.getAttributes();
            List<String> groups = (List<String>) attributes.get("https://gitlab.org/claims/groups");

            // Map GitLab groups to roles
            Set<GrantedAuthority> authorities = new HashSet<>(oAuth2User.getAuthorities());
            authorities.add(new SimpleGrantedAuthority("ROLE_USER")); // Default role
            if (groups != null) {
                if (groups.contains("lsit-ken3239/roles/ClothingStore/Customer")) {
                    authorities.add(new SimpleGrantedAuthority("ROLE_CUSTOMER"));
                }
                if (groups.contains("lsit-ken3239/roles/ClothingStore/Sales")) {
                    authorities.add(new SimpleGrantedAuthority("ROLE_SALES"));
                }
            }

            // Return OAuth2User with updated authorities
            return new DefaultOAuth2User(authorities, attributes, "name");
        };
    }
}
