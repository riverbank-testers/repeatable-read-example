package com.example.repeatablereadexample.service;

import com.example.repeatablereadexample.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class TransferServiceImpl implements TransferService {

    @Autowired
    private AccountRepository accountRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.REPEATABLE_READ)
    @Override
    public boolean transfer(
            String fromIban, String toIban, long cents) {
        boolean status = true;

        long fromBalance = accountRepository.getBalance(fromIban);

        if(fromBalance >= cents) {
            status &= accountRepository.addBalance(
                fromIban, (-1) * cents
            ) > 0;

            status &= accountRepository.addBalance(
                toIban, cents
            ) > 0;
        }

        return status;
    }
}
