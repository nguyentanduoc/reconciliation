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
public class CsvPlugin extends AbsReadPlugin implements ReadPlugin {

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
            if (!ObjectUtils.isEmpty(transaction)) {
                transactions.add(transaction);
            }
        }
        return transactions;
    }

    private Transaction assignTransaction(String[] values) {
        if (ObjectUtils.isEmpty(values)) {
            return null;
        }
        Transaction transaction = new Transaction();
        transaction.setDate(this.getDate(values));
        transaction.setContent(values[INDEX_CONTENT]);
        transaction.setAmount(this.getAmount(values));
        transaction.setType(values[INDEX_TYPE]);
        return this.checkNull(transaction) ? null : transaction;

    }

    private LocalDateTime getDate(String[] values) {
        try {
            String dateStr = values[INDEX_DATE];
            return LocalDateTime.parse(dateStr, DateUtils.DATE_TIME_FORMATTER);
        } catch (Exception exception) {
            log.error(exception.getMessage(), exception);
            return null;
        }
    }

    private Double getAmount(String[] values) {
        try {
            return Double.parseDouble(values[INDEX_AMOUNT]);
        } catch (Exception exception) {
            log.error(exception.getMessage(), exception);
            return null;
        }
    }
}