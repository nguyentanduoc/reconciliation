package com.ntd.bank.authenticationservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class DefaultAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication.getName() == null || authentication.getCredentials() == null
                || authentication.getName().isEmpty() || authentication.getCredentials().toString().isEmpty()) {
            return null;
        }
        final String userName = authentication.getName();
        final String password = (String) authentication.getCredentials();
        UserDetails user = userDetailsService.loadUserByUsername(userName);
        return new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
