package com.ntd.bank.transactionservice.service;

import com.ntd.bank.transactionservice.enums.HistoryStatus;

public interface HistoryService {

    void saveHistory(String fileName, HistoryStatus status);
}
