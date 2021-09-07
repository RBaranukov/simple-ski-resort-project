package com.example.ski_resort.baranukov.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

@Configuration
@EnableAuthorizationServer
@AllArgsConstructor
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    @Override
    public void configure(ClientDetailsServiceConfigurer configurer) throws Exception {
        configurer
                .inMemory()
                .withClient("clientApp")
                .secret(passwordEncoder.encode("client-secret"))
                .authorizedGrantTypes("password", "refresh_token")
                .redirectUris("http://localhost:8081/oauth/login/client-app")
                .scopes()
                .resourceIds("ski-resort")
                .authorities("ADMIN", "MANAGER")
                .accessTokenValiditySeconds(6000)
                .refreshTokenValiditySeconds(30000);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security.allowFormAuthenticationForClients()
                .tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.userDetailsService(userDetailsService)
                .accessTokenConverter(accessTokenConverter())
                .authenticationManager(authenticationManager);
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        String signingKey = "SigningKey";
        converter.setSigningKey(signingKey);
        converter.setVerifierKey(signingKey);
        return converter;
    }
}