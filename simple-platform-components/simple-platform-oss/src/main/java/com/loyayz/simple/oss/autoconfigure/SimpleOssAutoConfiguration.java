package com.loyayz.simple.oss.autoconfigure;

import com.loyayz.simple.oss.SimpleOssProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author loyayz (loyayz@foxmail.com)
 */
@Configuration
public class SimpleOssAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(SimpleOssProperties.class)
    @ConfigurationProperties(prefix = "simple.oss")
    public SimpleOssProperties simpleOssProperties() {
        return new SimpleOssProperties();
    }

}
