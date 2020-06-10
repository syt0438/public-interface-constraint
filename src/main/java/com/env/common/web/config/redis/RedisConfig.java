package com.env.common.web.config.redis;

import com.env.common.config.CommonAutoConfiguration;
import com.env.common.constants.redis.CacheType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * Redis相关配置
 *
 * @author LinShu
 * Please contact me if you have any questions.
 * My e-mail is syt0438@163.com
 */
@EnableCaching
@AutoConfigureAfter(CommonAutoConfiguration.class)
@Configuration
@ConditionalOnProperty(name = "common.auto.redis", havingValue = "true")
public class RedisConfig extends CachingConfigurerSupport {

    @Autowired
    private Environment environment;

    @Autowired
    private RedisConnectionFactory connectionFactory;

    @Bean
    @ConditionalOnMissingBean(DefaultRedisTemplate.class)
    public DefaultRedisTemplate redisTemplate() {
        DefaultRedisTemplate defaultRedisTemplate = new DefaultRedisTemplate();
        defaultRedisTemplate.setConnectionFactory(connectionFactory);

        return defaultRedisTemplate;
    }

    @Bean
    @ConditionalOnMissingBean(StringRedisTemplate.class)
    public StringRedisTemplate stringRedisTemplate() {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(connectionFactory);

        return stringRedisTemplate;
    }

    @Bean
    @ConditionalOnMissingBean(CacheManager.class)
    public CacheManager cacheManager() {
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate());
        cacheManager.setExpires(CacheType.EXPIRES_MAP);

        return cacheManager;
    }

    @Bean
    @Override
    public CacheResolver cacheResolver() {
        return new CustomResolvingCacheResolver(cacheManager(), environment);
    }
}
