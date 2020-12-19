package com.loyayz.simple.db.autoconfigure;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.loyayz.simple.db.mybatis.AbstractTable;
import com.loyayz.simple.db.mybatis.extension.DefaultSqlInjector;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

/**
 * @author loyayz (loyayz@foxmail.com)
 */
@Configuration
@AutoConfigureAfter({SimpleDataPropertiesAutoConfiguration.class})
@AutoConfigureBefore({MybatisPlusAutoConfiguration.class})
public class SimpleMybatisPlusAutoConfiguration {
    private SimpleMybatisProperties mybatisProperties;

    public SimpleMybatisPlusAutoConfiguration(SimpleMybatisProperties mybatisProperties) {
        this.mybatisProperties = mybatisProperties;
    }

    @Bean
    @ConditionalOnClass(ISqlInjector.class)
    @ConditionalOnMissingBean(ISqlInjector.class)
    public ISqlInjector sqlInjector() {
        return new DefaultSqlInjector();
    }

    @Bean
    @ConditionalOnClass(MetaObjectHandler.class)
    @ConditionalOnMissingBean(MetaObjectHandler.class)
    public MetaObjectHandler metaObjectHandler() {
        return new MetaObjectHandler() {
            @Override
            public void insertFill(MetaObject metaObject) {
                Object originalObject = metaObject.getOriginalObject();
                if (originalObject instanceof AbstractTable) {
                    AbstractTable entity = (AbstractTable) originalObject;
                    Date current = new Date();
                    entity.setGmtCreate(current);
                    entity.setGmtModified(current);
                }
            }

            @Override
            public void updateFill(MetaObject metaObject) {
                Object originalObject = metaObject.getOriginalObject();
                if (originalObject instanceof AbstractTable) {
                    ((AbstractTable) originalObject).setGmtModified(new Date());
                }
            }
        };
    }

}
