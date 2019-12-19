package ru.otus.jdbc.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.api.annotations.Id;
import ru.otus.api.dao.ModelDao;
import ru.otus.api.dao.ModelDaoException;
import ru.otus.api.sessionmanager.SessionManager;
import ru.otus.jdbc.DbExecutor;
import ru.otus.jdbc.handler.ClassHandler;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ModelDaoJdbc<T> implements ModelDao {
    private static Logger logger = LoggerFactory.getLogger(ModelDao.class);

    private final SessionManagerJdbc sessionManager;
    private final DbExecutor<T> dbExecutor;

    public ModelDaoJdbc(SessionManagerJdbc sessionManager, DbExecutor<T> dbExecutor) {
        this.sessionManager = sessionManager;
        this.dbExecutor = dbExecutor;
    }

    @Override
    public void create(Object objectData) {
        if (ClassHandler.isAnnotated(objectData)) {
            //INSERT INTO `table_name`(column_1,column_2,...) VALUES (value_1,value_2,...);
            String fields = ClassHandler.getSql(objectData);
            var sql = new StringBuilder("INSERT INTO ").append(objectData.getClass().getSimpleName()).append(fields).append(" VALUES (");
            List<Object> params = ClassHandler.getParams(objectData);
            for (int idx = 0; idx < params.size(); idx++) {
                sql.append("?");
                if (idx < params.size()-1)
                    sql.append(",");
            }
            sql.append(")");
            System.out.println(sql);
            try {
                long id = dbExecutor.insertRecord(getConnection(), sql.toString(), params);
                logger.info("object {} was added to table", id);
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
                throw new ModelDaoException(e);
            }
        } else {
            logger.info("class " + objectData.getClass().getSimpleName() + " has not annotation " + Id.class.getSimpleName());
            logger.info(objectData.getClass().getSimpleName() + " was not created");
        }
    }

    @Override
    public void update(Object objectData) {
        //UPDATE `table_name` SET `column_name` = `new_value' [WHERE condition];
        var sql = new StringBuilder("UPDATE ").append(objectData.getClass().getSimpleName()).append(" SET ");

    }

    @Override
    public void createOrUpdate(Object objectData) {

    }

    @Override
    public Object load(long id, Class clazz) {
        return null;
    }

    @Override
    public SessionManager getSessionManager() {
        return sessionManager;
    }

    private Connection getConnection() {
        return sessionManager.getCurrentSession().getConnection();
    }

}
