package ru.otus.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.api.annotation.Id;
import ru.otus.jdbc.holder.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.sql.*;

public class DBExecutor<T> {
    private static Logger logger = LoggerFactory.getLogger(DBExecutor.class);
    private Class<?> clazz;
    private ClassMetaDataHolder classMetaDataHolder;
    private final SQLQueriesHolder sqlQueriesHolder;

    public DBExecutor(SQLQueriesHolder sqlQueriesHolder, ClassMetaDataHolder classMetaDataHolder) {
        this.classMetaDataHolder = classMetaDataHolder;
        this.sqlQueriesHolder = sqlQueriesHolder;
    }


    public long save(Connection connection, T obj) throws SQLException, IllegalAccessException {
//        connection = sessionManager.getCurrentSession().getConnection();
        Savepoint savepoint = connection.setSavepoint();

        Class<?> clazz = obj.getClass();

        String saveQuery = sqlQueriesHolder.saveSQL(clazz);
        Field[] allFields = classMetaDataHolder.getAllFields(clazz);

        try (PreparedStatement pst = connection.prepareStatement(saveQuery, Statement.RETURN_GENERATED_KEYS)) {
            for (int idx = 0; idx < allFields.length; idx++) {
                allFields[idx].setAccessible(true);
                pst.setObject(idx + 1, allFields[idx].get(obj));
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

    public T load(Connection connection, Class<T> clazz, long id) throws SQLException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
//        connection = sessionManager.getCurrentSession().;
        Savepoint savepoint = connection.setSavepoint();

        String loadSql = sqlQueriesHolder.loadSQL(clazz);

        try (PreparedStatement pst = connection.prepareStatement(loadSql)) {
            pst.setLong(1, id);
            try (ResultSet resultSet = pst.executeQuery()) {
                Constructor<T> constructor = classMetaDataHolder.getConstructor(clazz);
                Field[] fields = classMetaDataHolder.getAllFields(clazz);
                Object[] fieldValue = new Object[fields.length];
                resultSet.next();

                for (int idx = 0; idx < fieldValue.length; idx++) {
                    if (!fields[idx].isAnnotationPresent(Id.class) && !(Modifier.isStatic(fields[idx].getModifiers()) && Modifier.isFinal(fields[idx].getModifiers()))) {
                        fieldValue[idx] = resultSet.getObject(fields[idx].getName());
                    } else if (fields[idx].isAnnotationPresent(Id.class))
                        fieldValue[idx] = id;
                }
                return constructor.newInstance(fieldValue);
            }
        } catch (Exception e) {
            connection.rollback(savepoint);
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    public void update(Connection connection, T obj) throws SQLException {
//        connection = sessionManager.getCurrentSession().getConnection();
        Savepoint savepoint = connection.setSavepoint();

        clazz = obj.getClass();
        String updateSql = sqlQueriesHolder.updateSQL(clazz);
        Field[] fields = classMetaDataHolder.getFieldsWithoutIdAnnotation(clazz);

        try (PreparedStatement pst = connection.prepareStatement(updateSql)) {
            Field fieldId = classMetaDataHolder.getIdAnnotatedField(clazz);
            for (int idx = 0; idx < fields.length; idx++) {
                Field field = fields[idx];
                field.setAccessible(true);
                pst.setObject(idx+1, field.get(obj));
            }
            fieldId.setAccessible(true);
            pst.setObject(fields.length+1, fieldId.get(obj));
            pst.execute();
        } catch (Exception e) {
            connection.rollback(savepoint);
            logger.error(e.getMessage(), e);
        }
    }
}
