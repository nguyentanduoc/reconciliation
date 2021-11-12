package com.ntd.bank.transactionservice.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.OAuth2ClientContext;

@Slf4j
public class OAuth2FeignRequestInterceptor implements RequestInterceptor {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    private static final String BEARER_TOKEN_TYPE = "Bearer";

    private final OAuth2ClientContext oauth2ClientContext;

    public OAuth2FeignRequestInterceptor(OAuth2ClientContext oauth2ClientContext) {
        this.oauth2ClientContext = oauth2ClientContext;
    }

    @Override
    public void apply(RequestTemplate template) {
        if (template.headers().containsKey(AUTHORIZATION_HEADER)) {
            log.warn("The Authorization token has been already set");
        } else if (oauth2ClientContext.getAccessTokenRequest().getExistingToken() == null) {
            log.warn("Can not obtain existing token for request, if it is a non secured request, ignore.");
        } else {
            log.debug("Constructing Header {} for Token {}", AUTHORIZATION_HEADER, BEARER_TOKEN_TYPE);
            template.header(AUTHORIZATION_HEADER, String.format("%s %s", BEARER_TOKEN_TYPE,
                    oauth2ClientContext.getAccessTokenRequest().getExistingToken().toString()));
        }
    }
}