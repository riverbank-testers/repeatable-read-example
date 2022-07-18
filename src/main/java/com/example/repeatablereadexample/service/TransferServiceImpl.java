package com.example.repeatablereadexample.service;

import com.example.repeatablereadexample.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

@Slf4j
@Transactional(readOnly = true)
@Service
public class TransferServiceImpl implements TransferService {

    @Autowired
    private AccountRepository accountRepository;

    @Retryable(value = SQLException.class, maxAttempts = 5, backoff = @Backoff(random = true, delay = 100, maxDelay = 6000))
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    @Override
    public boolean transfer(String fromIban, String toIban, long withdrawalAmount) {
        boolean status = true;

        long fromBalance = accountRepository.getBalance(fromIban);

        log.error("FromBalance: {}, withdrawalAmount: {}", fromBalance, withdrawalAmount);
        if (fromBalance >= withdrawalAmount) {
            status &= accountRepository.addBalance(fromIban, (-1) * withdrawalAmount) > 0;

            status &= accountRepository.addBalance(toIban, withdrawalAmount) > 0;
        } else {
            log.error("No enough funds. Balance: {}, Withdrawal Amount: {}", fromBalance, withdrawalAmount);
        }

        return status;
    }
}
