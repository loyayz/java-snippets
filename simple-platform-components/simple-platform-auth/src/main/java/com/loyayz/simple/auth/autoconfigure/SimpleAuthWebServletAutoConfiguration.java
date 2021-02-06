package com.loyayz.simple.auth.autoconfigure;

import com.loyayz.simple.auth.core.AuthCorsProperties;
import com.loyayz.simple.auth.core.AuthCredentialsProperties;
import com.loyayz.simple.auth.core.authentication.AbstractAuthCredentialsExtractor;
import com.loyayz.simple.auth.core.authentication.AuthCredentials;
import com.loyayz.simple.auth.core.authentication.AuthCredentialsExtractor;
import com.loyayz.simple.auth.core.authorization.AuthPermissionProvider;
import com.loyayz.simple.auth.core.identity.AuthIdentityService;
import com.loyayz.simple.auth.security.DefaultAuthenticationManager;
import com.loyayz.simple.auth.security.SecurityToken;
import com.loyayz.simple.auth.security.web.servlet.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationManagerResolver;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * @author loyayz (loyayz@foxmail.com)
 */
@Configuration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@AutoConfigureBefore({SecurityAutoConfiguration.class})
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true)
@RequiredArgsConstructor
public class SimpleAuthWebServletAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(AuthenticationPermissionHandler.class)
    public AuthenticationPermissionHandler authenticationPermissionHandler(AuthPermissionProvider permissionProvider) {
        return new DefaultAuthenticationPermissionHandler(permissionProvider);
    }

    @Bean
    @ConditionalOnMissingBean(AuthenticationManagerResolver.class)
    public AuthenticationManagerResolver<HttpServletRequest> authenticationManagerResolver(AuthIdentityService identityService) {
        AuthenticationManager manager = new DefaultAuthenticationManager(identityService);
        return context -> manager;
    }

    @Bean
    @ConditionalOnMissingBean(AuthenticationConverter.class)
    public AuthenticationConverter authenticationConverter(AuthCredentialsProperties credentialsProperties) {
        AuthCredentialsExtractor<HttpServletRequest> extractor =
                new AbstractAuthCredentialsExtractor<HttpServletRequest>(credentialsProperties) {
                    @Override
                    protected String getHeaderToken(HttpServletRequest request, String headerName) {
                        return request.getHeader(headerName);
                    }

                    @Override
                    protected String getParamToken(HttpServletRequest request, String paramName) {
                        return request.getParameter(paramName);
                    }
                };
        return request -> {
            AuthCredentials credentials = extractor.extract(request);
            return new SecurityToken(credentials);
        };
    }

    @Bean
    @ConditionalOnMissingBean(AuthenticationFilter.class)
    public AuthenticationFilter authenticationFilter(AuthenticationPermissionHandler permissionHandler,
                                                     AuthenticationManagerResolver<HttpServletRequest> managerResolver,
                                                     AuthenticationConverter converter) {
        AuthenticationSuccessHandler successHandler = (request, response, authentication) -> {

        };
        AuthenticationFailureHandler failureHandler = new HttpStatusAuthFailureHandler(HttpStatus.UNAUTHORIZED);

        AuthenticationFilter filter = new AuthenticationFilter(managerResolver, converter);
        filter.setRequestMatcher(permissionHandler.requiresAuthenticationMatcher());
        filter.setSuccessHandler(successHandler);
        filter.setFailureHandler(failureHandler);
        return filter;
    }

    @Bean
    @ConditionalOnMissingBean({WebSecurityConfigurerAdapter.class, AbstractWebSecurityAdapter.class})
    public WebSecurityConfigurerAdapter webSecurityConfigurerAdapter(AuthenticationFilter authenticationFilter,
                                                                     AuthenticationPermissionHandler permissionHandler,
                                                                     AuthCorsProperties corsProperties) {
        return new DefaultWebSecurityAdapter(authenticationFilter, permissionHandler, corsProperties);
    }

    @Bean
    public FilterRegistrationBean disableAutoRegistration(AuthenticationFilter authenticationFilter) {
        FilterRegistrationBean registration = new FilterRegistrationBean<>(authenticationFilter);
        registration.setEnabled(false);
        return registration;
    }

}
