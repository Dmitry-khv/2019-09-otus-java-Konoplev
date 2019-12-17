package ru.otus.h2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Table {
    private static Logger logger = LoggerFactory.getLogger("Table");

    public void createTables(DataSource dataSource) throws SQLException {
        createUserTable(dataSource);
        createAccountTable(dataSource);
    }

    public void createUserTable(DataSource dataSource) throws SQLException {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement pst = connection
                    .prepareStatement("create table user(id long(20) NOT NULL auto_increment, name varchar(255), age int(3))")) {
            pst.executeUpdate();
        }
        logger.info("User table created");
    }

    public void createAccountTable(DataSource dataSource) throws SQLException {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement pst = connection
                    .prepareStatement("create table account(no long(20) NOT NULL auto_increment, type varchar(255), rust double)")) {
            pst.executeUpdate();
        }
        logger.info("Account table created");
    }


}
