package com.tiendaropa.infrastructure.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Estructura estandar de respuesta para errores de la API.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        String path,
        Map<String, String> validaciones
) {
    public static ErrorResponse of(int status, String error, String message, String path) {
        return new ErrorResponse(LocalDateTime.now(), status, error, message, path, null);
    }

    public static ErrorResponse conValidaciones(int status, String error, String message, String path, Map<String, String> validaciones) {
        return new ErrorResponse(LocalDateTime.now(), status, error, message, path, validaciones);
    }
}
