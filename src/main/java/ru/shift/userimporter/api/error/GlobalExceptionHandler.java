package ru.shift.userimporter.api.error;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgument(IllegalArgumentException e) {
        String message = e.getMessage();

        if (message.equals("Файл уже есть")) {
            return ResponseEntity.status(409).body(Map.of("message", message));
        }
        else if (message.equals("Файл не найден")) {
            return ResponseEntity.status(404).body(Map.of("message", message));
        }

        return ResponseEntity.status(400).body(Map.of("message", message));
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<Map<String, String>> handleIOException(IOException e) {
        return ResponseEntity.status(500).body(Map.of("message", e.getMessage()));
    }
}
