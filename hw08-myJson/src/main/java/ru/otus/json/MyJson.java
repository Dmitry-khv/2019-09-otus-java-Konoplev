package ru.otus.json;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

public class MyJson {
    private StringBuilder stringBuilder = new StringBuilder();
    private final Set<Object> primitiveTypes = getPrimitiveTypes();

    private static Set<Object> getPrimitiveTypes() {
        return new HashSet<>(Arrays.asList(Boolean.class,
                Integer.class, Double.class, Float.class, Byte.class, Long.class, Short.class));
    }

    public String toJson(Object object) {
        if (object == null)
            return null;
        if (isObject(object)) {
            stringBuilder.append("{");
            Field[] fields = object.getClass().getDeclaredFields();
            for (Field field : fields) {
                try {
                    Object fieldValue = getFieldValue(field, object);
                    if (fieldValue != null) {
                        stringBuilder.append("\"").append(field.getName()).append("\":");
                        navigateTree(fieldValue);
                    }
                } catch (IllegalAccessException | NullPointerException e) {
                }
            }
            checkLastComma();
            stringBuilder.append("}");
        } else {
            stringBuilder = new StringBuilder();
            navigateTree(object);
            checkLastComma();
        }
        return stringBuilder.toString();
    }

    public Object getFieldValue(Field field, Object object) throws IllegalAccessException {
        Object fieldValue = null;
        if (!(Modifier.isStatic(field.getModifiers()) && Modifier.isFinal(field.getModifiers()))) {
            field.setAccessible(true);
            fieldValue = field.get(object);
        }
        return fieldValue;
    }

    private void navigateTree(Object fieldValue) {
        if (fieldValue.getClass().isPrimitive() || primitiveTypes.contains(fieldValue.getClass())) {
            splitUpWithComma(fieldValue);
        } else if (fieldValue.getClass().equals(String.class) || fieldValue.getClass().equals(Character.class)) {
            splitUpWithQuotes(fieldValue);
        } else if (fieldValue.getClass().isArray()) {
            stringBuilder.append("[");
            int lengthArray = Array.getLength(fieldValue);
            for (int i = 0; i < lengthArray; i++) {
                Object element = Array.get(fieldValue, i);
                navigateTree(element);
            }
            checkLastComma();
            stringBuilder.append("],");
        } else if (fieldValue instanceof List) {
            stringBuilder.append("[");
            List<?> list = (List<?>) fieldValue;
            for (Object element : list) {
                navigateTree(element);
            }

            checkLastComma();
            stringBuilder.append("],");
        } else {
            toJson(fieldValue);
            stringBuilder.append(",");
        }
    }

    public void splitUpWithComma(Object fieldValue) {
        stringBuilder.append(fieldValue)
                .append(",");
    }

    public void splitUpWithQuotes(Object fieldValue) {
        stringBuilder.append("\"")
                .append(fieldValue)
                .append("\",");
    }

    private boolean isObject(Object object) {
        return !object.getClass().isPrimitive()
                && !primitiveTypes.contains(object.getClass())
                && !object.getClass().equals(Character.class)
                && !object.getClass().equals(String.class)
                && (!object.getClass().isArray()
                && !(object instanceof List));
    }

    public void checkLastComma() {
        if (stringBuilder.codePointAt(stringBuilder.length() - 1) == ',')
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
    }
}
