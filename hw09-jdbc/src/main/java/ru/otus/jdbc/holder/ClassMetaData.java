package ru.otus.jdbc.holder;

import ru.otus.api.annotation.Id;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClassMetaData {
    private final Class<Id> ID_ANNOTATION = Id.class;
    private final Class<?> clazz;
    private Constructor constructor;
    private Field fieldWithIdAnnotation;
    private Field[] allFields;
    private Field[] fieldsWithoutIdAnnotation;

    public ClassMetaData(Class<?> clazz) {
        this.clazz = clazz;
    }

    private Constructor generateConstructor() throws NoSuchMethodException {
        allFields = clazz.getDeclaredFields();
        Class<?>[] paramClasses = new Class[allFields.length];
        for(int idx = 0; idx < paramClasses.length; idx++) {
            paramClasses[idx] = allFields[idx].getType();
        }
        return clazz.getConstructor(paramClasses);
    }

    private Field generateFieldWithIdAnnotation() {
        Field[] allFields = clazz.getDeclaredFields();
        for (Field field : allFields) {
            if (field.isAnnotationPresent(ID_ANNOTATION)) {
                fieldWithIdAnnotation = field;
                break;
            }
        }
        return fieldWithIdAnnotation;
    }

    private Field[] generateAllFields() {
        return clazz.getDeclaredFields();
    }

    private Field[] generateFieldsWithoutIdAnnotation() {
        allFields = clazz.getDeclaredFields();
        List<Field> fieldsWithoutId = new ArrayList<>();
        Arrays.stream(allFields).filter(field -> !field.equals(fieldWithIdAnnotation)).forEach(field -> fieldsWithoutId.add(field));
        fieldsWithoutIdAnnotation = fieldsWithoutId.toArray(new Field[fieldsWithoutId.size()]);
        return fieldsWithoutIdAnnotation;
    }

    public Constructor getConstructor() throws NoSuchMethodException {
        if(constructor == null)
            constructor = generateConstructor();
        return constructor;
    }

    public Field getFieldWithIdAnnotation() {
        if (fieldWithIdAnnotation == null)
            fieldWithIdAnnotation = generateFieldWithIdAnnotation();
        return fieldWithIdAnnotation;
    }

    public Field[] getAllFields() {
        if (allFields == null)
            allFields = generateAllFields();
        return allFields;
    }

    public Field[] getFieldsWithoutIdAnnotation() {
        if (fieldsWithoutIdAnnotation == null)
            fieldsWithoutIdAnnotation = generateFieldsWithoutIdAnnotation();
        return fieldsWithoutIdAnnotation;
    }
}
