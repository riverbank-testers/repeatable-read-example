package com.example.repeatablereadexample.repository;

import com.example.repeatablereadexample.model.Account;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author David
 * @date 012, 12-Jul-22 09:38 *
 * To God be the Glory
 */
@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {
    @Query(value = """
            SELECT balance
            FROM account
            WHERE iban = :iban
            """,
            nativeQuery = true)
    long getBalance(@Param("iban") String iban);

    @Query(value = """
            UPDATE account
            SET balance = balance + :cents
            WHERE iban = :iban
            """,
            nativeQuery = true)
    @Modifying
    @Transactional
    int addBalance(@Param("iban") String iban, @Param("cents") long cents);

    @Query(value = """
            update account 
            set balance = :amount
            where iban = :iban
            """, nativeQuery = true)
    @Modifying
    @Transactional
    int setBalance(@Param("iban") String iban, @Param("amount") long amount);
}
