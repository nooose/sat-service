package com.sat.common.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sat.auth.application.dto.TokenPair;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseUtils {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static <T> void setResponse(HttpServletResponse response, HttpStatus status, T body) {
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");
        try {
            response.getWriter().write(OBJECT_MAPPER.writeValueAsString(body));
        } catch (IOException e) {
            throw new IllegalArgumentException(TokenPair.class.getSimpleName() + " JSON 직렬화 실패");
        }
    }
}
