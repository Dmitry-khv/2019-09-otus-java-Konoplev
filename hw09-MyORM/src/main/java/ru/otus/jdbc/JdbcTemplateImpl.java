package ru.otus.jdbc;

import ru.otus.api.dao.ModelDao;
import ru.otus.api.model.Model;
import ru.otus.api.service.DBService;
import ru.otus.api.service.DBServiceImpl;
import ru.otus.orm.ClassHandler;

public class JdbcTemplateImpl<T extends ModelDao> implements JdbcTemplate {
    private final DBService dbService;

    private final ClassHandler classHandler;

    public JdbcTemplateImpl(T object) {
        classHandler = new ClassHandler();
        dbService = new DBServiceImpl<>(object);
    }

    @Override
    public void create(Object objectData) {

    }

    @Override
    public void update(Object objectData) {

    }

    @Override
    public Object load(long id, Class clazz) {
        return null;
    }
}
