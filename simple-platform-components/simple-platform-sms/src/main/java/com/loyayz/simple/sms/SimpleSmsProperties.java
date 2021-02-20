package com.loyayz.simple.sms;

import lombok.Data;

/**
 * @author loyayz (loyayz@foxmail.com)
 */
@Data
public class SimpleSmsProperties {

    /**
     * 短信服务提供者
     * aliyun,tencent,qiniu
     */
    private String provider;
    /**
     * 区域
     */
    private String region;
    /**
     * accessKey
     */
    private String accessKey;
    /**
     * secretKey
     */
    private String secretKey;
    /**
     * 短信签名
     */
    private String signName;

}
