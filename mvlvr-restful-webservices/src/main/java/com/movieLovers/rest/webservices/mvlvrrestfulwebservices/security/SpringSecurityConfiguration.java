package com.movieLovers.rest.webservices.mvlvrrestfulwebservices.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SpringSecurityConfiguration {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpreq) throws Exception {
        // makes sure all requets are authenticated
        httpreq.authorizeHttpRequests(auth -> auth.anyRequest().authenticated());
        // Enable basic authentication
        httpreq.httpBasic(withDefaults());
        // Disable CSRF
        httpreq.csrf().disable();
        return httpreq.build();
    }
}
