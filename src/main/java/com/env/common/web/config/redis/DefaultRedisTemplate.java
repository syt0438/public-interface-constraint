package com.env.common.web.config.redis;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author LinShu
 * Please contact me if you have any questions.
 * My e-mail is syt0438@163.com
 */
public class DefaultRedisTemplate extends RedisTemplate<String, Object> {
    public DefaultRedisTemplate() {
        RedisSerializer<String> stringSerializer = new StringRedisSerializer();
        GenericJackson2JsonRedisSerializer jacksonSerializer = new GenericJackson2JsonRedisSerializer();

        setKeySerializer(stringSerializer);
        setValueSerializer(jacksonSerializer);
        setHashKeySerializer(stringSerializer);
        setHashValueSerializer(jacksonSerializer);
    }

    public DefaultRedisTemplate(RedisConnectionFactory connectionFactory) {
        this();
        setConnectionFactory(connectionFactory);
        afterPropertiesSet();
    }
}
