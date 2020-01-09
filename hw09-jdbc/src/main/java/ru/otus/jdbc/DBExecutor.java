package ru.otus.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.api.annotation.Id;
import ru.otus.jdbc.reflection.ClassHandler;
import ru.otus.jdbc.reflection.SqlGenerator;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.sql.*;

public class DBExecutor<T> {
    private final Connection connection;
    private static Logger logger = LoggerFactory.getLogger(DBExecutor.class);
    private SqlGenerator<T> sqlGenerator;

    public DBExecutor(Connection connection) {
        this.connection = connection;
    }


    public long save(T obj) throws SQLException, IllegalAccessException {
        checkAnnotation(obj);
        Class<?> clazz = obj.getClass();
        Savepoint savepoint = connection.setSavepoint();

        if(sqlGenerator == null)
            sqlGenerator = new SqlGenerator<>();

        String saveSql = sqlGenerator.save(obj);
        Field[] fields = ClassHandler.getAllFields(clazz);

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

        if(sqlGenerator == null)
            sqlGenerator = new SqlGenerator<>();

        String loadSql = sqlGenerator.load(clazz);

        try (PreparedStatement pst = connection.prepareStatement(loadSql)) {
            pst.setLong(1, id);
            try (ResultSet resultSet = pst.executeQuery()) {
                resultSet.next();
                Constructor<T> constructor = ClassHandler.getConstructor(clazz);
                Field[] fields = ClassHandler.getAllFields(clazz);
                Object[] fieldValue = new Object[fields.length];

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

    public void update(T obj) throws SQLException {
        checkAnnotation(obj);
        Class<?> clazz = obj.getClass();
        Savepoint savepoint = connection.setSavepoint();

        if(sqlGenerator == null)
            sqlGenerator = new SqlGenerator<>();

        String updateSql = sqlGenerator.update(obj);
        Field[] fields = ClassHandler.getFieldsWithoutIdField(clazz);

        try (PreparedStatement pst = connection.prepareStatement(updateSql)) {
            Field fieldId = ClassHandler.getFieldWithAnnotationId(clazz);
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

    private void checkAnnotation(T obj) throws SQLException {
        boolean isAnnotated = false;
        Class<?> clazz = obj.getClass();

        Field[] fields = ClassHandler.getAllFields(clazz);
        for (Field field : fields) {
            if (field.isAnnotationPresent(Id.class))
                isAnnotated = true;
        }
        if(!isAnnotated)
            throw new SQLException(clazz.getSimpleName() +  " doesn't have annotation " + Id.class.getSimpleName());
    }
}
