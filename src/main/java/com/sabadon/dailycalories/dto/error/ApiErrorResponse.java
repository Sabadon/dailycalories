package com.sabadon.dailycalories.dto.error;

import com.sabadon.dailycalories.enums.ApiErrorCode;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.Map;

@Schema(description = "Сущность информации об ошибке")
public record ApiErrorResponse(
        @Schema(description = "Код ошибки")
        ApiErrorCode code,
        @Schema(description = "Сообщение ошибки")
        String message,
        @Schema(description = "Время когда произошла ошибка")
        LocalDateTime timestamp,
        @Schema(description = "Детали ошибки")
        Map<String, String> details
) {
    public ApiErrorResponse(ApiErrorCode code, String message, Map<String, String> details) {
        this(code, message, LocalDateTime.now(), details);
    }

    public ApiErrorResponse(ApiErrorCode code, String message) {
        this(code, message, LocalDateTime.now(), null);
    }
}
