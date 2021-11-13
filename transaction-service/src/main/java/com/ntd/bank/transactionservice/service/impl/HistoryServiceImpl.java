package com.ntd.bank.transactionservice.service.impl;

import com.ntd.bank.transactionservice.communication.HistoryClient;
import com.ntd.bank.transactionservice.dto.client.HistoryDto;
import com.ntd.bank.transactionservice.enums.HistoryStatus;
import com.ntd.bank.transactionservice.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class HistoryServiceImpl implements HistoryService {

    @Autowired
    private HistoryClient historyClient;

    public void saveHistory(String fileName, HistoryStatus status) {
        HistoryDto historyDto = HistoryDto.builder()
                .fileName(fileName)
                .status(status)
                .updatedAt(LocalDateTime.now())
                .build();
        historyClient.save(historyDto);
    }
}
