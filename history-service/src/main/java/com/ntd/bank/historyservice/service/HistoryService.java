package com.ntd.bank.historyservice.service;

import com.ntd.bank.historyservice.dto.HistoryDto;
import com.ntd.bank.historyservice.dto.PageResult;
import org.springframework.data.domain.Pageable;

public interface HistoryService {

    HistoryDto save(HistoryDto historyDto);

    PageResult<HistoryDto> getAll(Pageable pageable);
}
