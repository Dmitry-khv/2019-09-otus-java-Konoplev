package ru.otus.jdbc;

import checkers.oigj.quals.O;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.api.annotation.Id;
import ru.otus.jdbc.reflection.SqlGenerator;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.sql.*;
import java.util.List;

public class DBExecutor<T> {
    private final Connection connection;
    private static Logger logger = LoggerFactory.getLogger(DBExecutor.class);

    public DBExecutor(Connection connection) {
        this.connection = connection;
    }


    public long save(T obj) throws SQLException, IllegalAccessException {
        checkAnnotation(obj);
        Savepoint savepoint = connection.setSavepoint();
        String saveSql = new SqlGenerator<>().save(obj);
        Field[] fields = obj.getClass().getDeclaredFields();

        try (PreparedStatement pst = connection.prepareStatement(saveSql, Statement.RETURN_GENERATED_KEYS)) {
            for (int idx = 0; idx < fields.length; idx++) {
                fields[idx].setAccessible(true);
                pst.setObject(idx + 1, fields[idx].get(obj));
            }
            pst.executeUpdate();
            try (ResultSet resultSet = pst.getGeneratedKeys()) {
                resultSet.next();
                return resultSet.getLong(1);
            }
        } catch (SQLException | IllegalAccessException e) {
            connection.rollback(savepoint);
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    public T load(Class<T> clazz, long id) throws SQLException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Savepoint savepoint = connection.setSavepoint();
        String loadSql = new SqlGenerator<T>().load(clazz);

        try (PreparedStatement pst = connection.prepareStatement(loadSql)) {
            pst.setLong(1, id);
            try (ResultSet resultSet = pst.executeQuery()) {
                resultSet.next();
                Field[] fields = clazz.getDeclaredFields();
                Class[] paramClasses = new Class[fields.length];
                Object[] fieldValue = new Object[fields.length];
                for(int idx = 0; idx < paramClasses.length; idx++) {
                    paramClasses[idx] = fields[idx].getType();
                }
                Constructor<T> constructor = clazz.getConstructor(paramClasses);
                for (int idx = 0; idx < fieldValue.length; idx++) {
                    if (!fields[idx].isAnnotationPresent(Id.class) && !(Modifier.isStatic(fields[idx].getModifiers()) && Modifier.isFinal(fields[idx].getModifiers()))) {
                        fieldValue[idx] = resultSet.getObject(fields[idx].getName());
                    } else if (fields[idx].isAnnotationPresent(Id.class))
                        fieldValue[idx] = id;
                }
                T obj = constructor.newInstance(fieldValue);
                return obj;
            }
        } catch (Exception e) {
            connection.rollback(savepoint);
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    public void update(T obj) throws SQLException {
        checkAnnotation(obj);
        Savepoint savepoint = connection.setSavepoint();
        String updateSql = new SqlGenerator<T>().update(obj);
        Field[] fields = obj.getClass().getDeclaredFields();

        try (PreparedStatement pst = connection.prepareStatement(updateSql)) {
            Field fieldId = null;
            int paramIdx = 1;
            for (int idx = 0; idx < fields.length; idx++) {
                Field field = fields[idx];
                field.setAccessible(true);
                if (field.isAnnotationPresent(Id.class))
                    fieldId = field;
                else {
                    pst.setObject(paramIdx, field.get(obj));
                    paramIdx++;
                }
            }
            pst.setObject(fields.length, fieldId.get(obj));
            pst.execute();
        } catch (Exception e) {
            connection.rollback(savepoint);
            logger.error(e.getMessage(), e);
//            throw e;
        }
    }

    private void checkAnnotation(T obj) throws SQLException {
        boolean isAnnotated = false;
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Id.class))
                isAnnotated = true;
        }
        if(!isAnnotated)
            throw new SQLException(obj.getClass().getSimpleName() +  " doesn't have annotation " + Id.class.getSimpleName());
    }

}
