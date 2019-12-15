package ru.otus;

import ru.otus.h2.DataSourceH2;
import ru.otus.h2.Table;

import java.sql.SQLException;

public class JdbcTemplateDemo {

    public static void main(String[] args) throws SQLException {
        DataSourceH2 dataSource = new DataSourceH2();
        Table table = new Table();
        table.createTables(dataSource);
    }
}
