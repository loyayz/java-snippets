package com.loyayz.simple.sms;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author loyayz (loyayz@foxmail.com)
 */
@Data
public class SmsInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 短信模板
     */
    private String templateId;
    /**
     * 参数列表
     */
    private Map<String, Object> params;
    /**
     * 号码列表
     */
    private List<String> mobiles;

}
