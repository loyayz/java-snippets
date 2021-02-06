package com.loyayz.simple.auth.core.authentication;

/**
 * @author loyayz (loyayz@foxmail.com)
 */
@FunctionalInterface
public interface AuthCredentialsExtractor<T> {

    /**
     * 提取用于鉴权的凭证
     *
     * @param request 要提取凭证的请求
     * @return credentials never null
     */
    AuthCredentials extract(T request);

}
