package com.loyayz.simple.db.autoconfigure;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Properties;

/**
 * @author loyayz (loyayz@foxmail.com)
 */
@Data
@ConfigurationProperties(prefix = "simple.mybatis")
public class SimpleMybatisProperties {

    /**
     * id 生成器起始时间
     * 默认 +8 2020-01-01 00:00:00
     */
    private Long idEpoch = 1577808000000L;
    private PageHelperProperties pageHelper;

    public Properties getPageHelperProperties() {
        if (this.pageHelper == null) {
            return new Properties();
        }
        return this.pageHelper.getAllProperties();
    }

    /**
     * https://github.com/pagehelper/pagehelper-spring-boot
     */
    @Getter
    @Setter
    static class PageHelperProperties {
        private Boolean offsetAsPageNum;
        private Boolean rowBoundsWithCount;
        private Boolean pageSizeZero;
        private Boolean reasonable;
        private Boolean supportMethodsArguments;
        private String dialect;
        private String helperDialect;
        private Boolean autoRuntimeDialect;
        private Boolean autoDialect;
        private Boolean closeConn;
        private String params;
        private Boolean defaultCount;
        private String dialectAlias;
        private Properties properties;

        Properties getAllProperties() {
            Properties result = new Properties();
            if (this.properties != null) {
                result.putAll(this.properties);
            }
            this.addProperty(result, "offsetAsPageNum", offsetAsPageNum);
            this.addProperty(result, "rowBoundsWithCount", rowBoundsWithCount);
            this.addProperty(result, "pageSizeZero", pageSizeZero);
            this.addProperty(result, "reasonable", reasonable);
            this.addProperty(result, "supportMethodsArguments", supportMethodsArguments);
            this.addProperty(result, "dialect", dialect);
            this.addProperty(result, "helperDialect", helperDialect);
            this.addProperty(result, "autoRuntimeDialect", autoRuntimeDialect);
            this.addProperty(result, "autoDialect", autoDialect);
            this.addProperty(result, "closeConn", closeConn);
            this.addProperty(result, "params", params);
            this.addProperty(result, "defaultCount", defaultCount);
            this.addProperty(result, "dialectAlias", dialectAlias);
            return result;
        }

        private void addProperty(Properties properties, String key, Boolean value) {
            if (value == null) {
                return;
            }
            this.addProperty(properties, key, value.toString());
        }

        private void addProperty(Properties properties, String key, String value) {
            if (value == null) {
                return;
            }
            properties.setProperty(key, value);
        }
    }

}
