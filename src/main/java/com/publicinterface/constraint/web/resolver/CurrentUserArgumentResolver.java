package com.publicinterface.constraint.web.resolver;

import cn.hutool.json.JSONUtil;
import com.publicinterface.constraint.constraint.auth.UserDetailsConstraint;
import com.publicinterface.constraint.web.annotations.CurrentUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import static com.publicinterface.constraint.constants.Constants.USER_DETAILS_KEY;
import static com.publicinterface.constraint.constants.Constants.USER_ID_KEY;

/**
 * 用户参数解析器
 *
 * @author LinShu
 * Please contact me if you have any questions.
 * My e-mail is syt0438@163.com
 */
@Slf4j
public class CurrentUserArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(CurrentUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {

        if (methodParameter.hasParameterAnnotation(CurrentUser.class)) {
            return methodParameterTypeResolve(methodParameter, nativeWebRequest);
        }

        return null;
    }

    private Object methodParameterTypeResolve(MethodParameter methodParameter, NativeWebRequest nativeWebRequest) {
        Class<?> parameterType = methodParameter.getParameterType();
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);

        if (ClassUtils.isAssignable(parameterType, CharSequence.class)) {
            return request.getHeader(USER_ID_KEY);
        }

        if (ClassUtils.isAssignable(parameterType, UserDetailsConstraint.class)) {
            String userDetailsJson = request.getHeader(USER_DETAILS_KEY);

            if (StringUtils.isBlank(userDetailsJson)) {
                return null;
            }

            userDetailsJson = URLDecoder.decode(userDetailsJson, StandardCharsets.UTF_8);

            return JSONUtil.toBean(userDetailsJson, parameterType);
        }

        return null;
    }
}
