package ru.otus;

import ru.otus.api.dao.AccountDao;
import ru.otus.api.dao.UserDao;
import ru.otus.api.model.Account;
import ru.otus.api.model.User;
import ru.otus.api.service.DBServiceAccount;
import ru.otus.api.service.DBServiceAccountImpl;
import ru.otus.api.service.DBServiceUser;
import ru.otus.api.service.DBServiceUserImpl;
import ru.otus.h2.DataSourceH2;
import ru.otus.h2.TableCreator;
import ru.otus.jdbc.DBExecutor;
import ru.otus.jdbc.dao.AccountDaoJdbc;
import ru.otus.jdbc.dao.UserDaoJdbc;
import ru.otus.jdbc.holder.ClassMetaDataHolder;
import ru.otus.jdbc.holder.ClassMetaDataHolderImpl;
import ru.otus.jdbc.holder.SQLQueriesHolder;
import ru.otus.jdbc.holder.SQLQueriesHolderImpl;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

import javax.sql.DataSource;
import java.sql.SQLException;

public class Demo {
    public static void main(String[] args) throws SQLException {
        DataSource dataSource = new DataSourceH2();
        new TableCreator(User.class, dataSource).createUserTable();
        new TableCreator(Account.class, dataSource).createAccountTable();

        User user1 = new User(1, "Bob", 29);
        Account account1 = new Account(1, "acc",1.23);

        SessionManagerJdbc sessionManager = new SessionManagerJdbc(dataSource);

        ClassMetaDataHolder classMetaDataHolder = new ClassMetaDataHolderImpl(User.class, Account.class);
        SQLQueriesHolder sqlQueriesHolder = new SQLQueriesHolderImpl(classMetaDataHolder, User.class, Account.class);

        DBExecutor<User> userDBExecutor = new DBExecutor<>(sqlQueriesHolder, classMetaDataHolder);
        DBExecutor<Account> accountDBExecutor = new DBExecutor<>(sqlQueriesHolder, classMetaDataHolder);
        UserDao userDao = new UserDaoJdbc(sessionManager, userDBExecutor);
        AccountDao accountDao = new AccountDaoJdbc(sessionManager, accountDBExecutor);
        DBServiceUser dbServiceUser = new DBServiceUserImpl(userDao);
        DBServiceAccount dbServiceAccount = new DBServiceAccountImpl(accountDao);

        dbServiceUser.saveUser(user1);
        dbServiceAccount.saveAccount(account1);
        user1.setName("John");
        account1.setType("prem");
        dbServiceUser.updateUser(user1);
        dbServiceAccount.updateAccount(account1);
        User newUser = dbServiceUser.getUser(1);
        Account newAccount = dbServiceAccount.getAccount(1);
        System.out.println(newUser.equals(user1));
        System.out.println(newAccount.equals(account1));
        System.out.println(newAccount);
    }
}
