package com.ntd.bank.transactionservice.communication;

import com.ntd.bank.transactionservice.dto.ApiResponse;
import com.ntd.bank.transactionservice.dto.client.HistoryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("history-service")
public interface HistoryClient {

    @PostMapping(path = "history")
    ApiResponse<HistoryDto> save(@RequestBody HistoryDto historyDto);
}
