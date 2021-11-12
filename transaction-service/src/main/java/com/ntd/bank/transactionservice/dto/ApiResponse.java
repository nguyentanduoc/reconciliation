package com.ntd.bank.transactionservice.dto;

import lombok.Data;

@Data
public class ApiResponse<T> {
    private int statusCode;
    private String message;
    private T result;

    public ApiResponse(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public ApiResponse(T result) {
        this.message = "success";
        this.statusCode = 200;
        this.result = result;
    }

    public ApiResponse() {
        this.message = "success";
        this.statusCode = 200;
    }
}
