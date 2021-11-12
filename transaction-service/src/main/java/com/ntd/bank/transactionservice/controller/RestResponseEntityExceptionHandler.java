package com.ntd.bank.transactionservice.controller;

import com.ntd.bank.transactionservice.dto.ApiResponse;
import com.ntd.bank.transactionservice.exception.NotFoundById;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {NotFoundById.class})
    protected ResponseEntity<ApiResponse<String>> handleBadRequest(RuntimeException ex, WebRequest request) {
        ApiResponse<String> result = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return ResponseEntity.ok(result);
    }
}
