package com.ddmtchr.soalab.api;

import com.ddmtchr.soalab.exception.PageableValidationException;
import com.ddmtchr.soalab.validation.EntityFieldValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ValidatingPageableResolver implements HandlerMethodArgumentResolver {

    private final PageableHandlerMethodArgumentResolver delegate;
    private final EntityFieldValidator entityFieldValidator;

    @Override
    public boolean supportsParameter(@NonNull MethodParameter parameter) {
        return delegate.supportsParameter(parameter);
    }

    @Override
    public @NonNull Pageable resolveArgument(
            @NonNull MethodParameter methodParameter,
            @Nullable ModelAndViewContainer mavContainer,
            @NonNull NativeWebRequest webRequest,
            @Nullable WebDataBinderFactory binderFactory) {

        Pageable pageable = delegate.resolveArgument(methodParameter, mavContainer, webRequest, binderFactory);

        PageableEntity annotation = methodParameter.getParameterAnnotation(PageableEntity.class);
        if (annotation != null) {
            Class<?> entityClass = annotation.entityClass();

            boolean valid = true;
            List<String> messages = new ArrayList<>();
            for (Sort.Order order : pageable.getSort()) {
                if (!entityFieldValidator.isValidFieldPath(entityClass, order.getProperty())) {
                    valid = false;
                    messages.add(String.format(
                            "Sorting by field '%s' unavailable for entity %s",
                            order.getProperty(), entityClass.getSimpleName()
                    ));
                }
            }
            if (!valid) {
                throw new PageableValidationException(String.join(" \n", messages));
            }
        }
        return pageable;
    }
}

