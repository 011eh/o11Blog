package com.o11eh.servicedemo.front.service;

import lombok.AllArgsConstructor;
import org.springframework.transaction.support.TransactionTemplate;

@AllArgsConstructor
public abstract class BaseService {

    protected final TransactionTemplate transactionTemplate;

}
