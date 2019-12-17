package ru.otus.jdbc;

import ru.otus.api.service.DBService;
import ru.otus.api.service.DBServiceImpl;
import ru.otus.orm.ClassHandler;

public class JdbcTemplateImpl<T> implements JdbcTemplate {
    private final DBService dbService;
    private final ClassHandler classHandler;

    public JdbcTemplateImpl(T object) {
        classHandler = new ClassHandler(object);
        dbService = new DBServiceImpl<>(object);
    }

    @Override
    public void create(Object objectData) {
        classHandler.generateQuery();
        classHandler.getParamList();
    }

    @Override
    public void update(Object objectData) {

    }

    @Override
    public Object load(long id, Class clazz) {
        return null;
    }
}
