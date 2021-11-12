package com.ntd.bank.transactionservice.service;

import com.ntd.bank.transactionservice.dto.PageResult;
import com.ntd.bank.transactionservice.dto.TransactionDto;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface TransactionService {

    TransactionDto save(TransactionDto transactionDto);

    void bulkUpload(MultipartFile multipartFile) throws IOException;

    PageResult<TransactionDto> getAll(Pageable pageable);

    TransactionDto findById(Long id);

    TransactionDto update(Long id, TransactionDto transactionDto);

    void delete(Long id);
}
