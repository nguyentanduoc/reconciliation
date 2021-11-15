package com.ntd.bank.transactionservice.component;

import com.ntd.bank.transactionservice.model.Transaction;
import org.springframework.util.ObjectUtils;

public abstract class AbsReadPlugin {

    protected static final int INDEX_DATE = 0;
    protected static final int INDEX_CONTENT = 1;
    protected static final int INDEX_AMOUNT = 2;
    protected static final int INDEX_TYPE = 3;

    protected boolean checkNull(Transaction transaction) {
        boolean typeIsNull = ObjectUtils.isEmpty(transaction.getType());
        boolean amountIsNull = ObjectUtils.isEmpty(transaction.getAmount());
        boolean contentIsNull = ObjectUtils.isEmpty(transaction.getContent());
        boolean dateIsNull = ObjectUtils.isEmpty(transaction.getDate());
        return typeIsNull && amountIsNull && contentIsNull && dateIsNull;
    }
}
