package ru.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.h2.DataSourceH2;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBServiceDemo {
    private static Logger logger = LoggerFactory.getLogger(DBServiceDemo.class);

    public static void main(String[] args) throws SQLException {
        DataSource dataSource = new DataSourceH2();
        DBServiceDemo demo = new DBServiceDemo();
        demo.createTable(dataSource);

    }

    private void createTable(DataSource dataSource) throws SQLException {
        try (Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection
                    .prepareStatement("create table user(id bigint(20) NOT NULL auto_increment, name varchar(50), age int(3))")) {
                preparedStatement.executeUpdate();
        }
    }
}
