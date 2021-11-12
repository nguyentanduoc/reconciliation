package com.ntd.bank.historyservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@EnableJpaAuditing
@Service
public class AuditorAwareImpl implements AuditorAware<Long> {

    @Autowired
    private TokenStore tokenStore;

    @Override
    public Optional<Long> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Object> additionalInfo = getAdditionalInfo(authentication);
        Long userId = Long.parseLong(additionalInfo.get("user_id").toString());
        return Optional.of(userId);
    }

    private Map<String, Object> getAdditionalInfo(Authentication authentication) {
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
        OAuth2AccessToken accessToken = tokenStore.readAccessToken(details.getTokenValue());
        return accessToken.getAdditionalInformation();
    }
}