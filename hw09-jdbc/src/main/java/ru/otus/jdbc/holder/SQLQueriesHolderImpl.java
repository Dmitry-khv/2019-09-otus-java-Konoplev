package ru.otus.jdbc.holder;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SQLQueriesHolderImpl implements SQLQueriesHolder {

    private final ClassMetaDataHolder classMetaDataHolder;
    private final Map<Class<?>, SQLQueries> sqlQueries;


    public SQLQueriesHolderImpl(ClassMetaDataHolder classMetaDataHolder, Class<?>...getClasses) {
        this.classMetaDataHolder = classMetaDataHolder;
        this.sqlQueries = new HashMap<>();
        Arrays.stream(getClasses).forEach(aClass -> sqlQueries.put(aClass, new SQLQueries(aClass)));
    }

    @Override
    public String saveSQL(Class<?> clazz) {
        Field[] allFields = classMetaDataHolder.getAllFields(clazz);
        return sqlQueries.get(clazz).getSaveQuery(allFields);
    }

    @Override
    public String updateSQL(Class<?> clazz) {
        Field[] fieldsWithoutId = classMetaDataHolder.getFieldsWithoutIdAnnotation(clazz);
        Field idField = classMetaDataHolder.getIdAnnotatedField(clazz);
        return sqlQueries.get(clazz).getUpdateQuery(fieldsWithoutId, idField);
    }

    @Override
    public String loadSQL(Class<?> clazz) {
        Field[] fieldsWithoutId = classMetaDataHolder.getFieldsWithoutIdAnnotation(clazz);
        Field idField = classMetaDataHolder.getIdAnnotatedField(clazz);
        return sqlQueries.get(clazz).getLoadQuery(fieldsWithoutId, idField);
    }
}
