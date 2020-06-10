package com.env.common.constraint.mvc;

import com.baomidou.mybatisplus.core.conditions.interfaces.Compare;
import com.baomidou.mybatisplus.core.conditions.interfaces.Func;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.env.common.constraint.domain.ModelConstraint;
import com.env.common.utils.DbUtils;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;

import java.lang.reflect.Type;

/**
 * QueryWrapper静态代理工厂，提供对DB字段的动态转换，从下划线命名转为驼峰命名
 *
 * @author LinShu
 * Please contact me if you have any questions.
 * My e-mail is syt0438@163.com
 */
public abstract class QueryWrapperProxyFactory {

    @SuppressWarnings("unchecked")
    public static <T extends ModelConstraint> QueryWrapper<T> proxy(final QueryWrapper<T> target, final Class<T> clazz) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback((MethodInterceptor) (o, method, args, methodProxy) -> {
            method.setAccessible(true);

            Class<?> declaringClass = method.getDeclaringClass();

            if (declaringClass != Compare.class && declaringClass != Func.class) {
                return method.invoke(target, args);
            }

            Type[] parameterTypes = method.getGenericParameterTypes();

            for (int i = 0; i < args.length; i++) {
                if ("R".equals(parameterTypes[i].getTypeName())) {
                    args[i] = DbUtils.findColumnByField(clazz, String.valueOf(args[i]));

                    continue;
                }

                if ("R[]".equals(parameterTypes[i].getTypeName())) {
                    String[] xargs = (String[]) args[i];

                    for (int j = 0; j < xargs.length; j++) {
                        xargs[j] = DbUtils.findColumnByField(clazz, String.valueOf(xargs[j]));
                    }

                    args[i] = xargs;
                }
            }

            return method.invoke(target, args);
        });

        return (QueryWrapper<T>) enhancer.create();
    }
}
