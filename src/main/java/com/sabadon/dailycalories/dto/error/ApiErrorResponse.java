package com.sabadon.dailycalories.dto.error;

import java.time.LocalDateTime;
import java.util.Map;

public record ApiErrorResponse(
        String code,
        String message,
        LocalDateTime timestamp,
        Map<String, String> details
) {
    public ApiErrorResponse(String code, String message, Map<String, String> details) {
        this(code, message, LocalDateTime.now(), details);
    }

    public ApiErrorResponse(String code, String message) {
        this(code, message, LocalDateTime.now(), null);
    }
}
