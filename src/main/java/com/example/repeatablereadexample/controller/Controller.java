package com.example.repeatablereadexample.controller;

import com.example.repeatablereadexample.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author David
 * @date 018, 18-Jul-22 11:08 *
 * To God be the Glory
 */
@RestController
@RequestMapping("/api/v1/test")
public class Controller {

    @Autowired
    TransferService transferService;

    @GetMapping("/init")
    public Boolean init() {
        final var transfer = transferService.transfer("Alice-123", "Bob-456", 5L);

        return transfer;
    }
}
