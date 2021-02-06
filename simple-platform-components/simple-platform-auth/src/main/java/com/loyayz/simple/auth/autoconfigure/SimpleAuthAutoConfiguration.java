package com.loyayz.simple.auth.autoconfigure;

import com.loyayz.simple.auth.core.AuthCorsProperties;
import com.loyayz.simple.auth.core.AuthCredentialsProperties;
import com.loyayz.simple.auth.core.authorization.AuthPermissionProvider;
import com.loyayz.simple.auth.core.authorization.DefaultAuthPermissionProvider;
import com.loyayz.simple.auth.core.identity.AuthIdentityCache;
import com.loyayz.simple.auth.core.identity.AuthIdentityCacheDataConverter;
import com.loyayz.simple.auth.core.identity.AuthIdentityService;
import com.loyayz.simple.auth.core.identity.impl.DefaultAuthIdentityCacheDataConverter;
import com.loyayz.simple.auth.core.identity.impl.DefaultAuthIdentityService;
import com.loyayz.simple.auth.core.identity.impl.JwtAuthIdentityCache;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author loyayz (loyayz@foxmail.com)
 */
@Configuration
public class SimpleAuthAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(AuthCorsProperties.class)
    @ConfigurationProperties(prefix = "simple.auth.cors")
    public AuthCorsProperties authCorsProperties() {
        class DefaultAuthCorsProperties extends LinkedHashMap<String, AuthCorsProperties.CorsConfig> implements AuthCorsProperties {
            @Override
            public Map<String, CorsConfig> getCorsConfigs() {
                return this;
            }
        }
        return new DefaultAuthCorsProperties();
    }

    @Bean
    @ConditionalOnMissingBean(AuthCredentialsProperties.class)
    @ConfigurationProperties(prefix = "simple.auth.credentials")
    public AuthCredentialsProperties authCredentialsConfiguration() {
        return new AuthCredentialsProperties();
    }

    @Bean
    @ConditionalOnMissingBean(AuthPermissionProvider.class)
    @ConfigurationProperties(prefix = "simple.auth.permission")
    public AuthPermissionProvider authPermissionProvider() {
        return new DefaultAuthPermissionProvider();
    }

    @Bean
    @ConditionalOnMissingBean(AuthIdentityCache.class)
    public AuthIdentityCache authIdentityCache(AuthCredentialsProperties credentialsProperties) {
        return new JwtAuthIdentityCache(credentialsProperties);
    }

    @Bean
    @ConditionalOnMissingBean(AuthIdentityCacheDataConverter.class)
    public AuthIdentityCacheDataConverter authIdentityCacheDataConverter() {
        return new DefaultAuthIdentityCacheDataConverter();
    }

    @Bean
    @ConditionalOnMissingBean(AuthIdentityService.class)
    public AuthIdentityService authIdentityService(AuthIdentityCache authIdentityCache,
                                                   AuthIdentityCacheDataConverter authIdentityCacheDataConverter) {
        return new DefaultAuthIdentityService(authIdentityCache, authIdentityCacheDataConverter);
    }

}
