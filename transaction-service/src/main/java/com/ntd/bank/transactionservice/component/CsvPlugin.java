package com.ntd.bank.transactionservice.component;

import com.ntd.bank.transactionservice.model.Transaction;
import com.ntd.bank.transactionservice.utils.DateUtils;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@Component
public class CsvPlugin implements ReadPlugin {

    @Override
    public boolean supports(String s) {
        return "csv".equalsIgnoreCase(s);
    }

    @Override
    public List<Transaction> read(MultipartFile multipartFile) throws Exception {
        Reader reader = new InputStreamReader(multipartFile.getInputStream());
        CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build();
        String[] values;
        List<Transaction> transactions = new LinkedList<>();

        while ((values = csvReader.readNext()) != null) {
            Transaction transaction = this.assignTransaction(values);
            transactions.add(transaction);
        }
        return transactions;
    }

    private Transaction assignTransaction(String[] values) {
        if (ObjectUtils.isEmpty(values)) {
            return null;
        }
        Transaction transaction = new Transaction();
        //get date
        String dateStr = values[0];
        LocalDateTime localDateTime = LocalDateTime.parse(dateStr, DateUtils.DATE_TIME_FORMATTER);
        transaction.setDate(localDateTime);
        //get content
        String content = values[1];
        transaction.setContent(content);

        //get Amount
        String amount = values[2];
        transaction.setAmount(Double.parseDouble(amount));

        //get Type
        String type = values[3];
        transaction.setType(type);

        return transaction;

    }
}