package ru.otus.jdbc.reflection;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class SqlGenerator<T> {

    private Field[] fields;
    private static Map<Class, String> sqlSave = new HashMap<>();
    private static Map<Class, String> sqlLoad = new HashMap<>();
    private static Map<Class, String> sqlUpdate = new HashMap<>();

    public String save(T obj) {
        //INSERT INTO `table_name`(column_1,column_2,...) VALUES (value_1,value_2,...);
        Class<?> clazz = obj.getClass();
        if (!sqlSave.containsKey(clazz)) {
            var field = new StringBuilder("(");
            var fieldValues = new StringBuilder("(");

            fields = ClassHandler.getAllFields(clazz);

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
            var sql = new StringBuilder("INSERT INTO ").append(clazz.getSimpleName()).append(field).append(" VALUES ").append(fieldValues);
            sqlSave.put(clazz, sql.toString());
        }
        System.out.println(sqlSave.get(clazz));
        return sqlSave.get(clazz);
    }

    public String load(Class<?> clazz) {
        if(!sqlLoad.containsKey(clazz)) {
            var sql = new StringBuilder("SELECT ");
            fields = ClassHandler.getFieldsWithoutIdField(clazz);

            for (int idx = 0; idx < fields.length; idx++) {
                sql.append(fields[idx].getName());
                if (idx < fields.length - 1) {
                    sql.append(", ");
                } else
                    sql.append(" FROM ").append(clazz.getSimpleName());
            }
            sql.append(" WHERE ").append(ClassHandler.getFieldWithAnnotationId(clazz).getName()).append(" = ?");
            sqlLoad.put(clazz, sql.toString());
        }
        System.out.println(sqlLoad.get(clazz));
        return sqlLoad.get(clazz);
    }

    public String update(T obj) throws SQLException {
        //UPDATE `table_name` SET `column_name` = `new_value' [WHERE condition];
        Class<?> clazz = obj.getClass();
        if (!sqlUpdate.containsKey(clazz)) {
            var sql = new StringBuilder("UPDATE ").append(clazz.getSimpleName()).append(" SET ");
            fields = ClassHandler.getFieldsWithoutIdField(clazz);

            for (int idx = 0; idx < fields.length; idx++) {
                    sql.append(fields[idx].getName());
                    sql.append(" = ");
                    sql.append("?");
                    if (idx < fields.length - 1) {
                        sql.append(", ");
                    }
                }
            sql.append(" WHERE ").append(ClassHandler.getFieldWithAnnotationId(clazz).getName()).append(" = ?");
            sqlUpdate.put(clazz, sql.toString());
        }
        return sqlUpdate.get(clazz);
    }
}
