package com.personalsandbox.workmanagement.api;

import com.personalsandbox.workmanagement.application.InvalidWorkHoursImportException;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class WorkHoursExceptionHandler {

    @ExceptionHandler(InvalidWorkHoursImportException.class)
    public ResponseEntity<Map<String, String>> handleInvalidImport(InvalidWorkHoursImportException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", ex.getMessage()));
    }
}
