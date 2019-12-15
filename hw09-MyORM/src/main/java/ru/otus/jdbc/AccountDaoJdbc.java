package ru.otus.jdbc;

import ru.otus.api.dao.AccountDao;
import ru.otus.api.model.Account;

import java.util.Optional;

public class AccountDaoJdbc implements AccountDao {
    @Override
    public Optional<Account> findByOn(long on) {
        return Optional.empty();
    }

    @Override
    public void saveAccount(Account account) {

    }
}
