package ru.otus.h2;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Table {

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
        System.out.println("User table created");
    }

    public void createAccountTable(DataSource dataSource) throws SQLException {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement pst = connection
                    .prepareStatement("create table account(no long(20) NOT NULL auto_increment, type varchar(255), rust double)")) {
            pst.executeUpdate();
        }
        System.out.println("Account table created");
    }


}
