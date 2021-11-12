package com.ntd.bank.transactionservice.dto.client;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ntd.bank.transactionservice.enums.HistoryStatus;
import com.ntd.bank.transactionservice.utils.DateUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class HistoryDto {
    private Long id;

    @DateTimeFormat(pattern = DateUtils.DATE_FORMAT)
    @JsonFormat(pattern = DateUtils.DATE_FORMAT)
    private LocalDateTime updatedAt;

    private Long updatedBy;

    private String fileName;

    private HistoryStatus status;
}
