package com.loyayz.simple.sms;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author loyayz (loyayz@foxmail.com)
 */
public interface SmsService {

    /**
     * 发送短信
     *
     * @param info 短信内容
     * @return 结果
     */
    default SmsResult send(SmsInfo info) {
        return this.send(info.getTemplateId(), info.getParams(), info.getMobiles());
    }

    /**
     * 发送短信
     *
     * @param templateId 短信模板
     * @param params     短信参数
     * @param mobile     手机号
     * @return 结果
     */
    default SmsResult send(String templateId, Map<String, Object> params, String mobile) {
        return this.send(templateId, params, Collections.singletonList(mobile));
    }

    /**
     * 发送短信
     *
     * @param templateId 短信模板
     * @param params     短信参数
     * @param mobiles    手机号列表
     * @return 结果
     */
    SmsResult send(String templateId, Map<String, Object> params, List<String> mobiles);

}
