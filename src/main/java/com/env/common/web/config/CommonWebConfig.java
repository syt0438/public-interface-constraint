package com.env.common.web.config;

import com.env.common.web.resolver.CurrentUserArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * @author LinShu
 * Please contact me if you have any questions.
 * My e-mail is syt0438@163.com
 */
public class CommonWebConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private CurrentUserArgumentResolver currentUserArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        super.addArgumentResolvers(argumentResolvers);

        argumentResolvers.add(currentUserArgumentResolver);
    }
}
