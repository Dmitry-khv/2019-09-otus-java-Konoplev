package ru.otus.jdbc.holder;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ClassMetaDataHolderImpl implements ClassMetaDataHolder {
    private final Map<Class<?>, ClassMetaData> metadataStore = new HashMap<>();

    public ClassMetaDataHolderImpl(Class<?>...getClasses) {
        Arrays.stream(getClasses).forEach(aClass -> metadataStore.put(aClass, new ClassMetaData(aClass)));
    }

    @Override
    public Constructor getConstructor(Class<?> clazz) throws NoSuchMethodException {
        return metadataStore.get(clazz).getConstructor();
    }

    @Override
    public Field[] getAllFields(Class<?> clazz) {
        return metadataStore.get(clazz).getAllFields();
    }

    @Override
    public Field[] getFieldsWithoutIdAnnotation(Class<?> clazz) {
        return metadataStore.get(clazz).getFieldsWithoutIdAnnotation();
    }

    @Override
    public Field getIdAnnotatedField(Class<?> clazz) {
        return metadataStore.get(clazz).getFieldWithIdAnnotation();
    }
}
