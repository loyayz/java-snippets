package com.loyayz.simple.oss;

import lombok.Data;

/**
 * @author loyayz (loyayz@foxmail.com)
 */
@Data
public class SimpleOssProperties {

    /**
     * 文件服务提供者
     * local,minio,ali,tencent,qiniu
     */
    private String provider;
    /**
     * 应用 id
     */
    private String appId;
    /**
     * 密钥
     */
    private String secretKey;
    /**
     * 对象存储服务的 URL
     */
    private String endpoint;
    /**
     * 区域简称
     */
    private String region;
    /**
     * 存储桶名称
     */
    private String bucketName;

}
