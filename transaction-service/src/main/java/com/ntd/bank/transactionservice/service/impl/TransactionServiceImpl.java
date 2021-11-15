package com.ntd.bank.transactionservice.service.impl;

import com.ntd.bank.transactionservice.component.ReadPlugin;
import com.ntd.bank.transactionservice.dto.PageResult;
import com.ntd.bank.transactionservice.dto.TransactionDto;
import com.ntd.bank.transactionservice.enums.HistoryStatus;
import com.ntd.bank.transactionservice.exception.NotFoundById;
import com.ntd.bank.transactionservice.model.Transaction;
import com.ntd.bank.transactionservice.repository.TransactionRepository;
import com.ntd.bank.transactionservice.service.HistoryService;
import com.ntd.bank.transactionservice.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private PluginRegistry<ReadPlugin, String> readPlugin;

    @Autowired
    private HistoryService historyService;

    @Override
    public TransactionDto save(TransactionDto transactionDto) {
        Transaction transaction = modelMapper.map(transactionDto, Transaction.class);
        transaction = transactionRepository.save(transaction);
        return modelMapper.map(transaction, TransactionDto.class);
    }

    @Override
    public void bulkUpload(MultipartFile multipartFile) {
        String fileName = multipartFile.getOriginalFilename();
        String extension = fileName.split("\\.")[1];
        try {
            ReadPlugin plugin = readPlugin.getPluginFor(extension).get();
            List<Transaction> transactions = plugin.read(multipartFile);
            if (!ObjectUtils.isEmpty(transactions)) {
                transactionRepository.saveAll(transactions);
            }
            historyService.saveHistory(fileName, HistoryStatus.SUCCESS);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            historyService.saveHistory(fileName, HistoryStatus.FAIL);
        }
    }

    @Override
    public PageResult<TransactionDto> getAll(Pageable pageable) {
        Page<Transaction> transactions = transactionRepository.findAll(pageable);
        List<TransactionDto> transactionDtoList = new LinkedList<>();
        for (Transaction transaction : transactions) {
            TransactionDto dto = modelMapper.map(transaction, TransactionDto.class);
            transactionDtoList.add(dto);
        }
        PageResult<TransactionDto> pageResult = new PageResult<>();
        pageResult.setResult(transactionDtoList);
        pageResult.setCurrentPage(transactions.getNumber());
        pageResult.setTotalPage(transactions.getTotalPages());
        pageResult.setTotalItems(transactions.getTotalElements());
        return pageResult;
    }

    @Override
    public TransactionDto findById(Long id) {
        Optional<Transaction> transaction = transactionRepository.findById(id);
        if (transaction.isEmpty()) {
            throw new NotFoundById("Transaction", id);
        }
        return modelMapper.map(transaction.get(), TransactionDto.class);
    }

    @Override
    public TransactionDto update(Long id, TransactionDto transactionDto) {
        Optional<Transaction> transaction = transactionRepository.findById(id);
        if (transaction.isEmpty()) {
            throw new NotFoundById("Transaction", id);
        }
        Transaction model = transaction.get();
        BeanUtils.copyProperties(transactionDto, model);
        model.setId(id);
        transactionRepository.save(model);
        return modelMapper.map(model, TransactionDto.class);
    }

    @Override
    public void delete(Long id) {
        transactionRepository.deleteById(id);
    }

}
