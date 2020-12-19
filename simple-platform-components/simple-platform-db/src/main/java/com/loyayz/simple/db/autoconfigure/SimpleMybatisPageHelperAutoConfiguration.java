package com.loyayz.simple.db.autoconfigure;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Properties;

/**
 * @author loyayz (loyayz@foxmail.com)
 */
@Configuration
@ConditionalOnBean(SqlSessionFactory.class)
@AutoConfigureAfter({SimpleDataPropertiesAutoConfiguration.class, SimpleMybatisPlusAutoConfiguration.class, MybatisPlusAutoConfiguration.class})
public class SimpleMybatisPageHelperAutoConfiguration implements InitializingBean {
    private List<SqlSessionFactory> sqlSessionFactoryList;
    private SimpleMybatisProperties mybatisProperties;

    public SimpleMybatisPageHelperAutoConfiguration(List<SqlSessionFactory> sqlSessionFactoryList,
                                                    SimpleMybatisProperties mybatisProperties) {
        this.sqlSessionFactoryList = sqlSessionFactoryList;
        this.mybatisProperties = mybatisProperties;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.addPageHelper();
    }

    private void addPageHelper() {
        Properties properties = this.mybatisProperties.getPageHelperProperties();
        PageInterceptor interceptor = new PageInterceptor();
        interceptor.setProperties(properties);
        for (SqlSessionFactory sqlSessionFactory : sqlSessionFactoryList) {
            sqlSessionFactory.getConfiguration().addInterceptor(interceptor);
        }
    }

}
