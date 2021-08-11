package com.example.ski_resort.baranukov.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId("ski-resort");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/ski-resort/**").hasAnyAuthority("ADMIN", "MANAGER")
                .antMatchers(HttpMethod.POST, "/ski-resort/guests/**").hasAnyAuthority("ADMIN", "MANAGER")
                .antMatchers(HttpMethod.PUT, "/ski-resort/guests").hasAnyAuthority("ADMIN", "MANAGER")
                .antMatchers(HttpMethod.DELETE, "/ski-resort/guests/**").hasAnyAuthority("ADMIN", "MANAGER")
                .antMatchers("/ski-resort/**").hasAuthority("ADMIN")
                .antMatchers("/h2-console/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .and()
                .csrf().disable().cors().disable();
        http.headers().frameOptions().disable();
    }

    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults(""); //remove Authority_ prefix;
    }
}
