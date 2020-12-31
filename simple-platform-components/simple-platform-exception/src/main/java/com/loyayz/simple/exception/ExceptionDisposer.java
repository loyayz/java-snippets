package com.loyayz.simple.exception;

import java.util.List;

/**
 * @author loyayz (loyayz@foxmail.com)
 */
public interface ExceptionDisposer {
    String DEFAULT_CODE = "500";
    int DEFAULT_STATUS = 500;
    String DEFAULT_LOG_LEVEL = "WARN";

    /**
     * 要处理的异常
     */
    List<Class<? extends Throwable>> exceptions();

    /**
     * 业务异常编码
     */
    default String code(Throwable e) {
        return DEFAULT_CODE;
    }

    /**
     * 业务异常信息
     */
    default String message(Throwable e) {
        return e.getMessage();
    }

    /**
     * http 状态码
     */
    default int status(Throwable e) {
        return DEFAULT_STATUS;
    }

    /**
     * 日志等级
     * TRACE,TRACE,DEBUG,INFO,WARN,ERROR
     */
    default String logLevel(Throwable e) {
        return DEFAULT_LOG_LEVEL;
    }

    default ExceptionResult getResult(Throwable exception) {
        ExceptionResult result = new ExceptionResult();
        result.setStatus(this.status(exception));
        result.setCode(this.code(exception));
        result.setMessage(this.message(exception));
        return result;
    }

}
