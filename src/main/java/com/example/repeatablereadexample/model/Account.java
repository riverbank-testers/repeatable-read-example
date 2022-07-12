package com.example.repeatablereadexample.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigInteger;

/**
 * @author David
 * @date 012, 12-Jul-22 09:33 *
 * To God be the Glory
 */
@Getter
@Setter
@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String iban;
    private long balance;
    private String owner;

}
