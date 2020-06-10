package com.env.common.constants.redis;

import java.util.HashMap;
import java.util.Map;

/**
 * @author LinShu
 * Please contact me if you have any questions.
 * My e-mail is syt0438@163.com
 */
public abstract class CacheType {

    public static final String USER_INFO = "USER_INFO";

    public static final Map<String, Long> EXPIRES_MAP = new HashMap<>();

    static {
        EXPIRES_MAP.put(USER_INFO, 60 * 60 * 24 * 15L);
    }

}
