package com.ddmtchr.soalab.api;

import com.ddmtchr.soalab.exception.PageableValidationException;
import com.ddmtchr.soalab.util.ReflectionUtil;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ValidatingPageableResolver extends PageableHandlerMethodArgumentResolver {

    @Override
    public @NonNull Pageable resolveArgument(
            @NonNull MethodParameter methodParameter,
            @Nullable ModelAndViewContainer mavContainer,
            @NonNull NativeWebRequest webRequest,
            @Nullable WebDataBinderFactory binderFactory) {

        Pageable pageable = super.resolveArgument(methodParameter, mavContainer, webRequest, binderFactory);

        PageableEntity annotation = methodParameter.getParameterAnnotation(PageableEntity.class);
        if (annotation != null) {
            Class<?> entityClass = annotation.entityClass();

            Set<String> classFieldNames = ReflectionUtil.getAllFieldsFromCache(entityClass).stream().map(Field::getName).collect(Collectors.toSet());

            boolean valid = true;
            List<String> messages = new ArrayList<>();
            for (Sort.Order order : pageable.getSort()) {
                if (!classFieldNames.contains(order.getProperty())) {
                    valid = false;
                    messages.add(String.format("Sorting by field '%s' unavailable for entity %s",
                            order.getProperty(), entityClass.getSimpleName()));
                }
            }
            if (!valid) {
                throw new PageableValidationException(String.join(" \n", messages));
            }
        }
        return pageable;
    }
}

