package com.loyayz.simple.db.autoconfigure;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author loyayz (loyayz@foxmail.com)
 */
@Configuration
@EnableConfigurationProperties({SimpleMybatisProperties.class})
public class SimpleDataPropertiesAutoConfiguration {

}
