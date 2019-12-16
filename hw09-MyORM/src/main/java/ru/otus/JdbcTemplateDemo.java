package ru.otus;

import checkers.units.quals.A;
import ru.otus.api.dao.AccountDao;
import ru.otus.api.dao.ModelDao;
import ru.otus.api.dao.UserDao;
import ru.otus.api.model.Account;
import ru.otus.api.model.Model;
import ru.otus.api.model.User;
import ru.otus.api.service.DBServiceImpl;
import ru.otus.h2.DataSourceH2;
import ru.otus.h2.Table;
import ru.otus.jdbc.DbExecutor;
import ru.otus.jdbc.JdbcTemplate;
import ru.otus.jdbc.JdbcTemplateImpl;
import ru.otus.jdbc.dao.AccountDaoJdbc;
import ru.otus.jdbc.dao.UserDaoJdbc;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

import java.sql.SQLException;
import java.util.Optional;

public class JdbcTemplateDemo {

    public static void main(String[] args) throws SQLException {
        DataSourceH2 dataSource = new DataSourceH2();
        Table table = new Table();
        table.createTables(dataSource);
        SessionManagerJdbc sessionManager = new SessionManagerJdbc(dataSource);
        DbExecutor<User> userDbExecutor = new DbExecutor<>();
        DbExecutor<Account> accountDbExecutor = new DbExecutor<>();

        UserDao userDao = new UserDaoJdbc(sessionManager, userDbExecutor);
        AccountDao accountDao = new AccountDaoJdbc(sessionManager, accountDbExecutor);
        User hip = new User("Hip", 30);
        User hop = new User("Hop", 31);
        Account drum = new Account("Drum", 13);
        Account bass = new Account("Bass", 14);
        JdbcTemplate<User> userJdbcTemplate = new JdbcTemplateImpl<>(userDao);
        JdbcTemplate<Account> accountJdbcTemplate = new JdbcTemplateImpl<>(accountDao);

        userJdbcTemplate.create(hip);
        userJdbcTemplate.create(hop);
        accountJdbcTemplate.create(drum);
        accountJdbcTemplate.create(bass);
        hip.setAge(35);
        userJdbcTemplate.update(hip);


//        DBServiceImpl dbServiceUser = new DBServiceImpl(userDao);
//        JdbcTemplate jdbcTemplate = new JdbcTemplateImpl();




        bass.setNo(666);
    }
}
