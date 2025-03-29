package com.befoo.befoo.domain.controller;

import com.befoo.befoo.domain.dto.CustomUserDetails;
import io.micrometer.common.lang.NonNull;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 테스트에서 @AuthenticationPrincipal 애노테이션이 붙은 파라미터를 처리하기 위한 리졸버
 */
public class TestAuthArgumentResolver implements HandlerMethodArgumentResolver {

    private final CustomUserDetails userDetails;

    public TestAuthArgumentResolver(CustomUserDetails userDetails) {
        this.userDetails = userDetails;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(AuthenticationPrincipal.class) != null;
    }

    @Override
    public Object resolveArgument(@NonNull MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  @NonNull NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        return userDetails;
    }
} 