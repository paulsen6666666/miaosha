package com.example.fast.easy.common;

import org.assertj.core.util.Lists;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author paul
 * @Date 2022/7/26 22:17
 */

/**
 * 模拟配置中心, nacos/zookeeper/redis/MySQL等均可。
 */
class ConfigCenter {
    private static Map<String, String> sendMsgConfig = new HashMap<>();
    static {
        sendMsgConfig.put("bizType1", "email,sms");
        sendMsgConfig.put("bizType2", "sms");
    }
    // 根据业务获取配置，新业务，配置key-value即可
    public static List<String> getStrategy(String bizType) {
        return Lists.newArrayList(sendMsgConfig.get(bizType).split(","));
    }
}