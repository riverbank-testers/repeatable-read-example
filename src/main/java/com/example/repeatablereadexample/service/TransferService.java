package com.example.repeatablereadexample.service;

/**
 * @author David
 * @date 012, 12-Jul-22 09:40 *
 * To God be the Glory
 */
public interface TransferService {
    boolean transfer(
            String fromIban, String toIban, long cents);
}
