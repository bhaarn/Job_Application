package com.bits.job.portal.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Map;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(LocalDateTime timestamp, String message, String errorCode, Map<String, String> errors) {
    public ErrorResponse(LocalDateTime timestamp, String message, String errorCode) {
        this(timestamp, message, errorCode, null);
    }
}
