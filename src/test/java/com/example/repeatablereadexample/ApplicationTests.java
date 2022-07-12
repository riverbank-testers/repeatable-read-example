package com.example.repeatablereadexample;

import com.example.repeatablereadexample.repository.AccountRepository;
import com.example.repeatablereadexample.service.TransferService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@SpringBootTest
class ApplicationTests {

    @Resource
    TransferService transferService;

    @Resource
    AccountRepository accountRepository;

    @Test
    public void testParallelExecution()
            throws InterruptedException {

        int threadCount = 10;

        assertEquals(10L, accountRepository.getBalance("Alice-123"));
        assertEquals(0L, accountRepository.getBalance("Bob-456"));

        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch endLatch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            new Thread(() -> {
                try {
                    startLatch.await();

                    transferService.transfer(
                            "Alice-123", "Bob-456", 5L
                    );
                } catch (Exception e) {
                    log.error("Transfer failed", e);
                } finally {
                    endLatch.countDown();
                }
            }).start();
        }
        startLatch.countDown();
        endLatch.await();

        log.info(
                "Alice's balance {}",
                accountRepository.getBalance("Alice-123")
        );
        log.info(
                "Bob's balance {}",
                accountRepository.getBalance("Bob-456")
        );
    }

}
