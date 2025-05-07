package com.zerobase.weather.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeParseException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<String> handleDateParseError(DateTimeParseException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("날짜 형식이 잘못되었습니다. yyyy-MM-dd 형식으로 입력해주세요.");
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleGeneralError(RuntimeException e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("서버에서 문제가 발생했습니다: " + e.getMessage());
    }
}
