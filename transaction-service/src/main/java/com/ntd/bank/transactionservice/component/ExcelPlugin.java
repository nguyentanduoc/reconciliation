package com.ntd.bank.transactionservice.component;

import com.ntd.bank.transactionservice.model.Transaction;
import com.ntd.bank.transactionservice.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@Component
public class ExcelPlugin extends AbsReadPlugin implements ReadPlugin {

    @Override
    public boolean supports(String s) {
        return "xlsx".equalsIgnoreCase(s) || "xls".equalsIgnoreCase(s);
    }

    @Override
    public List<Transaction> read(MultipartFile multipartFile) throws Exception {
        String extension = multipartFile.getOriginalFilename().split("\\.")[1];
        InputStream inputStream = new BufferedInputStream(multipartFile.getInputStream());
        Workbook workbook;
        if (extension.equalsIgnoreCase("xlsx")) {
            workbook = new XSSFWorkbook(inputStream);
        } else {
            workbook = new HSSFWorkbook(inputStream);
        }
        Sheet sheet = workbook.getSheetAt(0);
        return this.sheetToTransactionList(sheet);
    }

    private List<Transaction> sheetToTransactionList(Sheet sheet) {
        if (ObjectUtils.isEmpty(sheet)) {
            return null;
        }
        List<Transaction> transactions = new LinkedList<>();
        Iterator<Row> rows = sheet.rowIterator();
        while (rows.hasNext()) {
            Row row = rows.next();
            if (row.getRowNum() == 0) {
                continue;
            }
            Transaction transaction = this.assignTransaction(row);
            if (!ObjectUtils.isEmpty(transaction)) {
                transactions.add(transaction);
            }
        }
        return transactions;
    }

    private Transaction assignTransaction(Row row) {
        if (ObjectUtils.isEmpty(row)) {
            return null;
        }
        Transaction transaction = new Transaction();
        transaction.setDate(this.getDate(row));
        transaction.setContent(this.getContent(row));
        transaction.setAmount(this.getAmount(row));
        transaction.setType(this.getType(row));
        return this.checkNull(transaction) ? null : transaction;
    }


    private LocalDateTime getDate(Row row) {
        try {
            String dateStr = row.getCell(INDEX_DATE).getStringCellValue();
            return LocalDateTime.parse(dateStr, DateUtils.DATE_TIME_FORMATTER);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return null;
        }
    }

    private String getContent(Row row) {
        try {
            return row.getCell(INDEX_CONTENT).getStringCellValue();
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return null;
        }
    }

    private Double getAmount(Row row) {
        try {
            return row.getCell(INDEX_AMOUNT).getNumericCellValue();
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return null;
        }
    }

    private String getType(Row row) {
        try {
            return row.getCell(INDEX_TYPE).getStringCellValue();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return null;
        }
    }
}
