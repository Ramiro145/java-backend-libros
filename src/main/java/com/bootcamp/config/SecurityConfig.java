package com.bootcamp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authz->
                authz.anyRequest().authenticated() // todas las solicitudes entrantes deben estar autenticadas
        ).csrf(csrfConfig -> csrfConfig.disable())
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    //el authenticationManagerbuilder esta siendo inyectado automaticamente por spring
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin").password("{noop}admin123").roles("ADMIN")
                .and()
                .withUser("bibliotecario").password("{noop}bibl123").roles("BIBL")
                .and()
                .withUser("coordinador").password("{noop}coor123").roles("COOR");
    }
}
