package com.env.common.config;

import com.env.common.web.config.CommonWebConfig;
import com.env.common.web.resolver.CurrentUserArgumentResolver;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 自动配置参数解析器等
 *
 * @author LinShu
 * Please contact me if you have any questions.
 * My e-mail is syt0438@163.com
 */
@Configuration
@ConditionalOnClass({
        WebMvcConfigurer.class,
        CommonWebConfig.class
})
@ConditionalOnProperty(name = "common.auto.webmvc", havingValue = "true")
public class CommonAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(CurrentUserArgumentResolver.class)
    public CurrentUserArgumentResolver currentUserArgumentResolver() {
        return new CurrentUserArgumentResolver();
    }

    @Bean
    @ConditionalOnMissingBean(CommonWebConfig.class)
    public CommonWebConfig commonWebConfig() {
        return new CommonWebConfig();
    }
}
