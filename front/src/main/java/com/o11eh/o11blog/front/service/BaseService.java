package com.o11eh.o11blog.front.service;

import lombok.AllArgsConstructor;
import org.springframework.transaction.support.TransactionTemplate;

@AllArgsConstructor
public abstract class BaseService {

    protected final TransactionTemplate transactionTemplate;

}
