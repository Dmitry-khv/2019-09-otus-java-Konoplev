package ru.otus.jdbc.handler;

import ru.otus.api.annotations.Id;

import java.io.BufferedReader;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class ClassHandler {

    public static String getSql(Object object) {
        Field[] fields = object.getClass().getDeclaredFields();
        StringBuilder sb = new StringBuilder("(");

        for (int idx = 0; idx < fields.length; idx++) {
            if (!fields[idx].isAnnotationPresent(Id.class)) {
                sb.append(fields[idx].getName());
                if (idx < fields.length - 1) sb.append(",");
            }
        }
        sb.append(")");
        return sb.toString();
    }

    public static boolean isAnnotated(Object objectData) {
        Field[] fields = objectData.getClass().getDeclaredFields();
        for(Field field : fields)
            if (field.isAnnotationPresent(Id.class))
                return true;
        return false;
    }

    public static List<Object> getParams(Object objectData) {
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

    public static Object getFieldValue(Field field, Object object) {
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
