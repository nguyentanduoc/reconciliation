package com.ntd.bank.transactionservice.exception;

public class NotFoundById extends RuntimeException {

    public NotFoundById(String model, Long id) {
        super(String.format("not found %s by id: %d", model, id));
    }
}
