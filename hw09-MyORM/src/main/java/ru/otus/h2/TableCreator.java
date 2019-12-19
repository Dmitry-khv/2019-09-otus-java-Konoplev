package ru.otus.h2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.DbServiceDemo;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TableCreator {
    private final Class<?> clazz;
    private final DataSource dataSource;
    private static Logger logger = LoggerFactory.getLogger(DbServiceDemo.class);


    public TableCreator(Class<?> clazz, DataSource dataSource) {
        this.clazz = clazz;
        this.dataSource = dataSource;
    }

    public void createTables() {
        try {
            createUserTable();
            logger.info("user table was created");
        } catch (SQLException e) {
            logger.info("user table was NOT created");
        }
        try {
            createAccountTable();
            logger.info("account table was created");
        } catch (SQLException e) {
            logger.info("account table was NOT created");
        }
    }

    public void createUserTable() throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement pst = connection.prepareStatement("create table user(id long(20) NOT NULL auto_increment, name varchar(255), age int(3))")) {
            pst.executeUpdate();
        }
    }

    public void createAccountTable() throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement pst = connection.prepareStatement("create table account(no long(20) NOT NULL auto_increment, type varchar(255), rest double)")) {
            pst.executeUpdate();
        }
    }


}
