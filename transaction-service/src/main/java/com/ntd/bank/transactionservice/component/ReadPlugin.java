package com.ntd.bank.transactionservice.component;

import com.ntd.bank.transactionservice.model.Transaction;
import org.springframework.plugin.core.Plugin;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ReadPlugin extends Plugin<String> {

    List<Transaction> read(MultipartFile multipartFile) throws Exception;
}
