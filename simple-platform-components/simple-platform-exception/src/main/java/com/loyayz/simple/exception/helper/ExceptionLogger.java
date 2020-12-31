package com.loyayz.simple.exception.helper;

/**
 * @author loyayz (loyayz@foxmail.com)
 */
@FunctionalInterface
public interface ExceptionLogger {

    /**
     * 异常日志
     */
    void write(ExceptionLoggerParam param);

}
