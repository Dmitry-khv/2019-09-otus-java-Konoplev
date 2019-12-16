package ru.otus.api.dao;

import ru.otus.api.model.Account;

import java.util.Optional;


public interface AccountDao extends ModelDao{
    Optional<Account> findByOn(long on);

    void saveAccount(Account account);
}
