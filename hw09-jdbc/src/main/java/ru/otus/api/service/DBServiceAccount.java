package ru.otus.api.service;

import ru.otus.api.model.Account;

import java.util.Optional;

public interface DBServiceAccount {
    void saveAccount(Account account);

    void updateAccount(Account account);

    Account getAccount(long no);
}
