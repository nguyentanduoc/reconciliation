package com.ntd.bank.transactionservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ntd.bank.transactionservice.utils.DateUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TransactionDto implements Serializable {

    private Long id;

    @DateTimeFormat(pattern = DateUtils.DATE_FORMAT)
    @JsonFormat(pattern = DateUtils.DATE_FORMAT)
    private LocalDateTime date;

    private String content;

    private Double amount;

    private String type;
}
