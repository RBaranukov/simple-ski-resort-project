package com.example.ski_resort.baranukov.security;

import com.example.ski_resort.baranukov.entity.Role;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests().antMatchers("/h2-console/**")
                .permitAll()
                .antMatchers(HttpMethod.GET, "/coaches", "/guests", "/ski-passes").hasRole(Role.MANAGER.name())
                .antMatchers(HttpMethod.POST, "/guests/**").hasRole(Role.MANAGER.name())
                .antMatchers(HttpMethod.PUT, "/guests").hasRole(Role.MANAGER.name())
                .antMatchers(HttpMethod.DELETE, "/guests/**").hasRole(Role.MANAGER.name())
                .antMatchers("/").hasRole(Role.ADMIN.name())
                .anyRequest()
                .authenticated();
        httpSecurity.csrf().ignoringAntMatchers("/h2-console/**");
        httpSecurity.headers().frameOptions().sameOrigin();
    }
}
