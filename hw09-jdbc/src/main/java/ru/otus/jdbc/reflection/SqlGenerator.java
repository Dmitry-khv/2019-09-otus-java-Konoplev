package ru.otus.jdbc.reflection;

import ru.otus.api.annotation.Id;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SqlGenerator<T> {

    public String save(T obj) {
        //INSERT INTO `table_name`(column_1,column_2,...) VALUES (value_1,value_2,...);
        var field = new StringBuilder("(");
        var fieldValues = new StringBuilder("(");
        List<Object> params = getParams(obj);

        Field[] fields = obj.getClass().getDeclaredFields();

        for (int idx = 0; idx < fields.length; idx++) {
            field.append(fields[idx].getName());
            fieldValues.append("?");
            if (idx < fields.length - 1) {
                field.append(",");
                fieldValues.append(",");
            }
        }
        field.append(")");
        fieldValues.append(")");
        var sql = new StringBuilder("INSERT INTO ").append(obj.getClass().getSimpleName()).append(field).append(" VALUES ").append(fieldValues);
        System.out.println(sql);
        return sql.toString();
    }

    public String load(Class clazz) {
        var sql = new StringBuilder("SELECT ");
        var condition = new StringBuilder();
        Field[] fields = clazz.getDeclaredFields();

        for (int idx = 0; idx < fields.length; idx++) {
            if (!fields[idx].isAnnotationPresent(Id.class)) {
                sql.append(fields[idx].getName());
                if (idx < fields.length - 1) {
                    sql.append(", ");
                } else
                    sql.append(" FROM ").append(clazz.getSimpleName());
            } else {
                condition.append(" WHERE ");
                condition.append(fields[idx].getName());
                condition.append(" = ?");
            }
        }
        sql.append(condition);
        System.out.println(sql);
        return sql.toString();
    }

    public String update(T obj) throws SQLException {
        //UPDATE `table_name` SET `column_name` = `new_value' [WHERE condition];

        var sql = new StringBuilder("UPDATE ").append(obj.getClass().getSimpleName()).append(" SET ");
        var condition = new StringBuilder();
        Field[] fields = obj.getClass().getDeclaredFields();

        for (int idx = 0; idx < fields.length; idx++) {
            if (!fields[idx].isAnnotationPresent(Id.class)) {
                sql.append(fields[idx].getName());
                sql.append(" = ");
                sql.append("?");
                if (idx < fields.length - 1) {
                    sql.append(", ");
                }
            } else if (fields[idx].isAnnotationPresent(Id.class)) {
                condition.append(" WHERE ");
                condition.append(fields[idx].getName());
                condition.append(" = ?");
            }
        }
        sql.append(condition);
        System.out.println(sql);
        return sql.toString();
    }

    public List<Object> getParams(T objectData) {
        List<Object> params = new ArrayList<>();
        Field[] fields = objectData.getClass().getDeclaredFields();

        for (int idx = 0; idx < fields.length; idx++) {
            if (!fields[idx].isAnnotationPresent(Id.class)) {
                Object fieldValue = getFieldValue(fields[idx], objectData);
                params.add(fieldValue);
            }
        }
        return params;
    }

    public Object getFieldValue(Field field, Object object) {
        Object fieldValue = null;
        if (!(Modifier.isStatic(field.getModifiers()) && Modifier.isFinal(field.getModifiers()))) {
            field.setAccessible(true);
            try {
                fieldValue = field.get(object);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return fieldValue;
    }
}
