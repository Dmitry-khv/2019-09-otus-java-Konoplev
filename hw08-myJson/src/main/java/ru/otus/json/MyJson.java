package ru.otus.json;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.List;

public class MyJson {
    private StringBuilder stringBuilder;

    public String toJson(Person object) {
        stringBuilder = new StringBuilder();
        Field[] fields;
        try {
            fields = object.getClass().getDeclaredFields();
        } catch (NullPointerException e) {
            return null;
        }

        stringBuilder.append("{");

        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object fieldValue = field.get(object);
                if (fieldValue != null) {
                    stringBuilder.append("\"").append(field.getName()).append("\":");
                    navigateTree(fieldValue);
                }
            } catch (IllegalAccessException e) {
            }
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    private void navigateTree(Object fieldValue) {
        Class fieldType = fieldValue.getClass();

        if (fieldType.equals(Boolean.class)) {
            primitiveHandling(fieldValue);
        } else if (fieldType.equals(Character.class)) {
            notPrimitivesHandling(fieldValue);
        } else if (fieldValue instanceof Number) {
            primitiveHandling(fieldValue);
        } else if (fieldType.equals(String.class)) {
            notPrimitivesHandling(fieldValue);
        } else if (fieldType.isArray()) {
            int lengthArray = Array.getLength(fieldValue);
            stringBuilder.append("[");
            for (int i = 0; i < lengthArray; i++) {
                Object element = Array.get(fieldValue, i);
                navigateTree(element);
            }
            if (stringBuilder.codePointAt(stringBuilder.length() - 1) == ',')
                stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            stringBuilder.append("],");
        } else if (fieldValue instanceof List) {
            stringBuilder.append("[");
            List<?> list = (List<?>) fieldValue;
            for (Object elementList : list) {
                navigateTree(elementList);
            }
            if (stringBuilder.codePointAt(stringBuilder.length() - 1) == ',')
                stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            stringBuilder.append("],");
        }
    }

    public void primitiveHandling(Object fieldValue) {
        stringBuilder.append(fieldValue)
                .append(",");
    }

    public void notPrimitivesHandling(Object fieldValue) {
        stringBuilder.append("\"")
                .append(fieldValue)
                .append("\",");
    }
}
