package ru.otus.jdbc.holder;


import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

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
            String names = Arrays.stream(fields).map(Field::getName).collect(Collectors.joining(","));
            String vals = Arrays.stream(fields).map(f -> "?").collect(Collectors.joining(","));
            save = String.format("INSERT INTO %s (%s) VALUES (%s)", clazz.getSimpleName(), names, vals);
        }
        return save;
    }

    public String getUpdateQuery(Field[] fields, Field fieldWithId) {
        if(update == null) {
            String condition = Arrays.stream(fields).map(Field::getName).collect(Collectors.joining("=?,"));
            update = String.format("UPDATE %s SET %s=? WHERE %s=?", clazz.getSimpleName(), condition, fieldWithId.getName());
        }
        return update;
    }

    public String getLoadQuery(Field[] fieldsWithoutId, Field fieldWithId) {
        if(load == null) {
            String condition = Arrays.stream(fieldsWithoutId).map(Field::getName).collect(Collectors.joining(","));
            load = String.format("SELECT %s FROM %s WHERE %s = ?", condition, clazz.getSimpleName(), fieldWithId.getName());
        }
        System.out.println(load);
        return load;
    }
}
