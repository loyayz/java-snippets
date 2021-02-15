package com.loyayz.simple.oss;

import java.io.File;
import java.io.InputStream;
import java.util.List;

/**
 * 文件服务
 *
 * @author loyayz (loyayz@foxmail.com)
 */
public interface SimpleFileService {

    /**
     * 获取文件信息
     *
     * @param bucketName 存储桶名称
     * @param fileName   文件名称
     * @return InputStream
     */
    SimpleOssFile get(String bucketName, String fileName);

    /**
     * 上传文件
     *
     * @param bucketName 存储桶名称
     * @param fileName   文件名称
     * @param file       文件
     * @return BladeFile
     */
    SimpleOssFile upload(String bucketName, String fileName, File file);

    /**
     * 上传文件
     *
     * @param bucketName 存储桶名称
     * @param fileName   文件名称
     * @param stream     文件流
     * @return BladeFile
     */
    SimpleOssFile upload(String bucketName, String fileName, InputStream stream);

    /**
     * 删除文件
     *
     * @param bucketName 存储桶名称
     * @param fileName   文件名称
     */
    void remove(String bucketName, String fileName);

    /**
     * 批量删除文件
     *
     * @param bucketName 存储桶名称
     * @param fileNames  文件名称列表
     */
    void remove(String bucketName, List<String> fileNames);

    /**
     * 获取文件完整路径
     *
     * @param bucketName 存储桶名称
     * @param fileName   文件名称
     * @return String
     */
    String filePath(String bucketName, String fileName);

    /**
     * 拷贝文件
     *
     * @param originBucketName 原存储桶名称
     * @param fileName         原文件名称
     * @param destBucketName   目标存储桶名称
     */
    default void copy(String originBucketName, String fileName, String destBucketName) {
        this.copy(originBucketName, fileName, destBucketName, fileName);
    }

    /**
     * 拷贝文件
     *
     * @param originBucketName 原存储桶名称
     * @param originFileName   原文件名称
     * @param destBucketName   目标存储桶名称
     * @param destFileName     目标文件名称
     */
    void copy(String originBucketName, String originFileName, String destBucketName, String destFileName);

    /**
     * 迁移文件
     *
     * @param originBucketName 原存储桶名称
     * @param fileName         原文件名称
     * @param destBucketName   目标存储桶名称
     */
    default void move(String originBucketName, String fileName, String destBucketName) {
        this.move(originBucketName, fileName, destBucketName, fileName);
    }

    /**
     * 拷贝文件
     *
     * @param originBucketName 原存储桶名称
     * @param originFileName   原文件名称
     * @param destBucketName   目标存储桶名称
     * @param destFileName     目标文件名称
     */
    void move(String originBucketName, String originFileName, String destBucketName, String destFileName);

}
