package ru.otus.jdbc.reflection;

import ru.otus.api.annotation.Id;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class ClassHandler {
    //    private List<Object> params = new ArrayList<>();
    private static Map<Class, Field> fieldWithAnnotationId = new HashMap<>();
    private static final Class ID_ANNOTATION = Id.class;
    private static Map<Class, Field[]> allFields = new HashMap<>();
    private static Map<Class, Field[]> fieldsWithoutIdAnnotation = new HashMap<>();
    private static Map<Class, Constructor> constructors = new HashMap<>();


    public static Field[] getAllFields(Class clazz) {
        if (!allFields.containsKey(clazz))
            allFields.put(clazz, clazz.getDeclaredFields());
        return allFields.get(clazz);
    }

    public static Field[] getFieldsWithoutIdField(Class clazz) {
        if (!fieldsWithoutIdAnnotation.containsKey(clazz)) {
            Field[] fields = clazz.getDeclaredFields();
            List<Field> fieldsWithoutId = new ArrayList<>();
            Arrays.stream(fields).filter(field -> !field.isAnnotationPresent(ID_ANNOTATION)).forEach(field -> fieldsWithoutId.add(field));
//        Arrays.stream(fields).filter(field -> field.isAnnotationPresent(ID_ANNOTATION)).forEach(field -> fieldWithAnnotationId = field);
            fieldsWithoutIdAnnotation.put(clazz, fieldsWithoutId.toArray(new Field[fieldsWithoutId.size()]));
        }
        return fieldsWithoutIdAnnotation.get(clazz);
    }

    public static Field getFieldWithAnnotationId(Class clazz) {
        if(!fieldWithAnnotationId.containsKey(clazz)) {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(ID_ANNOTATION)) {
                    fieldWithAnnotationId.put(clazz, field);
                    break;
                }
            }
        }
        return fieldWithAnnotationId.get(clazz);
    }

    public static Constructor getConstructor(Class clazz) throws NoSuchMethodException {
        if (!constructors.containsKey(clazz)) {
            Field[] fields = clazz.getDeclaredFields();
            Class[] paramClasses = new Class[fields.length];
            Object[] fieldValue = new Object[fields.length];
            for(int idx = 0; idx < paramClasses.length; idx++) {
                paramClasses[idx] = fields[idx].getType();
            }
            Constructor constructor = clazz.getConstructor(paramClasses);
            constructors.put(clazz, constructor);
        }
        return constructors.get(clazz);
    }

//    public ClassHandler() {
//        Field[] fields = clazz.getDeclaredFields();
//        Arrays.stream(fields).filter(field -> !field.isAnnotationPresent(ID_ANNOTATION)).forEach(field -> params.add(field));
//        Arrays.stream(fields).filter(field -> field.isAnnotationPresent(ID_ANNOTATION)).forEach(field -> fieldWithAnnotationId = field);
//    }
}
