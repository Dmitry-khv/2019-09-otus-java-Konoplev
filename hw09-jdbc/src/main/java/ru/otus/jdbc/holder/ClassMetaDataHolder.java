package ru.otus.jdbc.holder;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public interface ClassMetaDataHolder {
    Constructor getConstructor(Class<?> clazz) throws NoSuchMethodException;
    Field[] getAllFields(Class<?> clazz);
    Field[] getFieldsWithoutIdAnnotation(Class<?> clazz);
    Field getIdAnnotatedField(Class<?> clazz);
}
