package com.publicinterface.constraint.web.redis;

import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.CacheOperationInvocationContext;
import org.springframework.cache.interceptor.SimpleCacheResolver;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.core.env.PropertyResolver;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * 自定义缓存解析器，使 cacheNames 支持 spEL 表达式
 *
 * @author LinShu
 * Please contact me if you have any questions.
 * My e-mail is syt0438@163.com
 */
public class CustomResolvingCacheResolver extends SimpleCacheResolver {

    private final PropertyResolver propertyResolver;
    private final ParameterNameDiscoverer parameterNameDiscoverer;
    private final SpelExpressionParser parser;


    public CustomResolvingCacheResolver(CacheManager cacheManager, PropertyResolver propertyResolver) {
        super(cacheManager);

        this.propertyResolver = propertyResolver;
        this.parameterNameDiscoverer = new DefaultParameterNameDiscoverer();
        this.parser = new SpelExpressionParser();
    }


    @Override
    protected Collection<String> getCacheNames(CacheOperationInvocationContext<?> context) {
        Collection<String> unresolvedCacheNames = super.getCacheNames(context);
        StandardEvaluationContext standardEvaluationContext = new StandardEvaluationContext(context);

        return unresolvedCacheNames.stream()
                .map(unresolvedCacheName -> {
                    Expression expression = parser.parseExpression(unresolvedCacheName);

                    return expression.getValue(standardEvaluationContext).toString();
                }).collect(Collectors.toList());
    }

}
