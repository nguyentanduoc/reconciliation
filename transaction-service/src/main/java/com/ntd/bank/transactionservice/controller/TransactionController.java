package com.ntd.bank.transactionservice.controller;

import com.ntd.bank.transactionservice.dto.PageResult;
import com.ntd.bank.transactionservice.dto.ApiResponse;
import com.ntd.bank.transactionservice.dto.TransactionDto;
import com.ntd.bank.transactionservice.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(path = "transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<ApiResponse<TransactionDto>> create(@RequestBody TransactionDto transactionDto) {
        TransactionDto transaction = transactionService.save(transactionDto);
        return ResponseEntity.ok(new ApiResponse<>(transaction));
    }

    @PostMapping(path = "bulk-upload")
    public ResponseEntity<ApiResponse<Void>> bulkUpload(@RequestParam("file") MultipartFile file) throws IOException {
        transactionService.bulkUpload(file);
        return ResponseEntity.ok(new ApiResponse<>());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResult<TransactionDto>>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) {
        Pageable paging = PageRequest.of(page, size);
        PageResult<TransactionDto> transactionDtoList = transactionService.getAll(paging);
        return ResponseEntity.ok(new ApiResponse<>(transactionDtoList));
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<ApiResponse<TransactionDto>> getById(@PathVariable("id") Long id) {
        TransactionDto transactionDto = transactionService.findById(id);
        return ResponseEntity.ok(new ApiResponse<>(transactionDto));
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<ApiResponse<TransactionDto>> updateById(
            @PathVariable("id") Long id, @RequestBody TransactionDto transactionDto) {
        TransactionDto result = transactionService.update(id, transactionDto);
        return ResponseEntity.ok(new ApiResponse<>(result));
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<ApiResponse<Void>> deleteById(@PathVariable("id") Long id) {
        transactionService.delete(id);
        return ResponseEntity.ok(new ApiResponse<>());
    }
}
