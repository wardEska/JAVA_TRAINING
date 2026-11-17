package com.lev2.common;

import com.lev2.common.dto.ApiResponse;
import org.jspecify.annotations.Nullable;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

// Scoped to our own controllers only: Spring Data REST and Actuator endpoints live in
// other packages and pick message converters bound to their own types (HAL /
// RepresentationModel), so wrapping their bodies in ApiResponse blows up at write time.
@ControllerAdvice(basePackages = "com.lev2.rest")
public class ResponseAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        Class<?> type = returnType.getParameterType();
        if (ApiResponse.class.equals(type)) {
            return false;
        }
        return !ApiResponse.class.isAssignableFrom(type);
    }

    @Override
    public @Nullable Object beforeBodyWrite(@Nullable Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body instanceof ApiResponse) {
            return body;
        }

        // 2. Ignore Spring Boot's default error objects (e.g., 404 Not Found or validation errors)
        if (body instanceof ProblemDetail) {
            return body;
        }

        // 3. Handle raw String return types
        if (body instanceof String) {
            return body;
        }



        return ApiResponse.success(body);
    }
}
