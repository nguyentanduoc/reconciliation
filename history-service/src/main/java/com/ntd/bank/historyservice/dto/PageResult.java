package com.ntd.bank.historyservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PageResult <T> {
    private List<T> result;
    private int currentPage;
    private long totalItems;
    private long totalPage;
}
