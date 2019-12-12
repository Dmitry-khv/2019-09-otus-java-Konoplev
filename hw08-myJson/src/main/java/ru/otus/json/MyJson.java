package ru.otus.json;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

public class MyJson {
    private final Set<Object> primitiveTypes = getPrimitiveTypes();

    private static Set<Object> getPrimitiveTypes() {
        return new HashSet<>(Arrays.asList(Boolean.class,
                Integer.class, Double.class, Float.class, Byte.class, Long.class, Short.class, Character.class));
    }

    public String toJson(Object object) {
        if (object == null)
            return null;
        return generateString(object);
    }

    public String generateString(Object object) {
        StringBuilder stringBuilder = new StringBuilder();
        if (isObject(object)) {
            stringBuilder.append("{");
            Field[] fields = object.getClass().getDeclaredFields();
            int count = 0;
            for (Field field : fields) {
                count++;
                try {
                    Object fieldValue = getFieldValue(field, object);
                    if (fieldValue != null) {
                        stringBuilder.append("\"").append(field.getName()).append("\":");
                        stringBuilder.append(processObject(fieldValue));
                        if(count < fields.length)
                            stringBuilder.append(",");
                    }
                } catch (IllegalAccessException | NullPointerException e) {
                }
            }
            stringBuilder.append("}");
        } else {
            stringBuilder.append(processObject(object));
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

    private String processObject(Object fieldValue) {
        StringBuilder stringBuilder = new StringBuilder();
        if (fieldValue.getClass().equals(String.class) || fieldValue.getClass().equals(Character.class)) {
            stringBuilder.append("\"")
                    .append(fieldValue)
                    .append("\"");
            return stringBuilder.toString();
        } else if (fieldValue.getClass().isPrimitive() || primitiveTypes.contains(fieldValue.getClass())) {
            return stringBuilder.append(fieldValue).toString();
        } else if (fieldValue.getClass().isArray()) {
            stringBuilder.append("[");
            int lengthArray = Array.getLength(fieldValue);
            for (int i = 0; i < lengthArray; i++) {
                Object element = Array.get(fieldValue, i);
                stringBuilder.append(processObject(element));
                if (i < lengthArray-1)
                    stringBuilder.append(",");
            }
            stringBuilder.append("]");
            return stringBuilder.toString();
        } else if (fieldValue instanceof Collection) {
            stringBuilder.append("[");
            Collection<?> list = (Collection<?>) fieldValue;
            Iterator iterator = list.iterator();
            while (iterator.hasNext()) {
                stringBuilder.append(processObject(iterator.next()));
                if (iterator.hasNext())
                    stringBuilder.append(",");
            }
            stringBuilder.append("]");
            return stringBuilder.toString();
        } else {
            return stringBuilder.append(toJson(fieldValue)).toString();
        }
    }

    private boolean isObject(Object object) {
        return !object.getClass().isPrimitive()
                && !primitiveTypes.contains(object.getClass())
                && !object.getClass().equals(String.class)
                && (!object.getClass().isArray()
                && !(object instanceof List));
    }
}