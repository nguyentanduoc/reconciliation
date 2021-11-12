package com.ntd.bank.transactionservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.shaded.json.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Base64;

@Slf4j
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Value("${security.public-key}")
    private String publicKey;

    @Autowired
    private ResourceServerProperties resourceServerProperties;

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        var converter = new AdditionalClaimsAccessTokenConverter();
        converter.setVerifierKey(getPubKey());
        return converter;
    }

    private String getPubKey() {
        return StringUtils.isEmpty(resourceServerProperties.getJwt().getKeyValue())
                ? getKeyFromAuthorizationServer()
                : resourceServerProperties.getJwt().getKeyValue();
    }

    private String getKeyFromAuthorizationServer() {
        ObjectMapper objectMapper = new ObjectMapper();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.AUTHORIZATION, encodeClient());
        HttpEntity<String> requestEntity = new HttpEntity<>(null, httpHeaders);
        String pubKey = new RestTemplate()
                .getForObject(resourceServerProperties.getJwt().getKeyUri(), String.class, requestEntity);
        try {
            JSONObject body = objectMapper.readValue(pubKey, JSONObject.class);
            return body.getAsString("value");
        } catch (IOException e) {
            log.error("Get public key error: {}", e.getMessage());
            pubKey = publicKey;
        }
        return pubKey;
    }

    private String encodeClient() {
        return "Basic " + Base64.getEncoder().encodeToString((resourceServerProperties.getClientId()
                + ":" + resourceServerProperties.getClientSecret()).getBytes());
    }
}
