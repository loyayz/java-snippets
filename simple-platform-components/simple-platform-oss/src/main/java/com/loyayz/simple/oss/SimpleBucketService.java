package com.loyayz.simple.oss;

/**
 * 存储桶服务
 *
 * @author loyayz (loyayz@foxmail.com)
 */
public interface SimpleBucketService {

    /**
     * 创建
     *
     * @param bucketName 存储桶名称
     */
    void create(String bucketName);

    /**
     * 删除
     *
     * @param bucketName 存储桶名称
     */
    void remove(String bucketName);

    /**
     * 清空存储桶
     *
     * @param bucketName 存储桶名称
     */
    void clean(String bucketName);

    /**
     * 拷贝存储桶
     *
     * @param originBucketName 原存储桶名称
     * @param destBucketName   目标存储桶名称
     */
    void copy(String originBucketName, String destBucketName);

    /**
     * 修改存储桶名称
     *
     * @param originBucketName 原存储桶名称
     * @param destBucketName   目标存储桶名称
     */
    void rename(String originBucketName, String destBucketName);

}
