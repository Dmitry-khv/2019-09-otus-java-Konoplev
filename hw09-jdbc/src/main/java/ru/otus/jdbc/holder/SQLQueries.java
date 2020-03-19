package ru.otus.jdbc.holder;


import java.lang.reflect.Field;

public class SQLQueries<T> {
    private String save;
    private String update;
    private String load;
    private Class<T> clazz;

    public SQLQueries(Class<T> clazz) {
        this.clazz = clazz;
    }

    public String getSaveQuery(Field[] fields){
        if(save == null) {
            var field = new StringBuilder("(");
            var fieldValues = new StringBuilder("(");

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
            save = new StringBuilder("INSERT INTO ").append(clazz.getSimpleName()).append(field).append(" VALUES ").append(fieldValues).toString();
        }
        return save;
    }

    public String getUpdateQuery(Field[] fields, Field fieldWithId) {
        if(update == null) {
            var sql = new StringBuilder("UPDATE ").append(clazz.getSimpleName()).append(" SET ");

            for (int idx = 0; idx < fields.length; idx++) {
                sql.append(fields[idx].getName());
                sql.append(" = ");
                sql.append("?");
                if (idx < fields.length - 1) {
                    sql.append(", ");
                }
            }
            update = sql.append(" WHERE ").append(fieldWithId.getName()).append(" = ?").toString();
        }
        return update;
    }

    public String getLoadQuery(Field[] fieldsWithoutId, Field fieldWithId) {
        if(load == null) {
            var sql = new StringBuilder("SELECT ");

            for (int idx = 0; idx < fieldsWithoutId.length; idx++) {
                sql.append(fieldsWithoutId[idx].getName());
                if (idx < fieldsWithoutId.length - 1) {
                    sql.append(", ");
                } else
                    sql.append(" FROM ").append(clazz.getSimpleName());
            }
            load = sql.append(" WHERE ").append(fieldWithId.getName()).append(" = ?").toString();
        }
        System.out.println(load);
        return load;
    }
}
