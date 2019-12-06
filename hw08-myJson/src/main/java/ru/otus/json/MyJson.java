package ru.otus.json;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class MyJson {
    private StringBuilder stringBuilder = new StringBuilder();
    private String className;

    public String toJson(Person object) throws IllegalAccessException {

        Class<?> myClass = object.getClass();
        stringBuilder.append("{");

        Field[] fields = myClass.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            navigateTree(field, object);
//            stringBuilder.append("\"").append(field.getName()).append("\"").append(":").append(field.get(object)).append(",");

        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        stringBuilder.append("}");
        System.out.println(fields.toString());
        return stringBuilder.toString();

    }

    private void navigateTree(Field field, Object object) {
        Object fieldValue;
        try {
            fieldValue = field.get(object);
        } catch (IllegalAccessException e) {
            return;
        }
        Class fieldType = fieldValue.getClass();
        stringBuilder.append("\"")
                .append(field.getName()).append("\":");

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
            if (fieldType.getComponentType().isPrimitive()) {
                for (int i = 0; i < lengthArray; i++) {
                    Object element = Array.get(fieldValue, i);
                    primitiveHandling(element);
                }
            } else {
                for (int i = 0; i < lengthArray; i++) {
                    Object element = Array.get(fieldValue, i);
                    notPrimitivesHandling(element);
                }
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            stringBuilder.append("],");
        }

        else if (field.getType().isAssignableFrom(List.class)) {
            System.out.println(field.getGenericType().getClass().getTypeName()+"\n");
            stringBuilder.append("[");
            System.out.println(field.getGenericType());
            List<?> list = (List<?>) fieldValue;



            if (!field.getGenericType().getClass().isPrimitive()) {
                stringBuilder.append(field.getGenericType());
            }
//            if (field.getGenericType().getClass().isPrimitive())
//                List<Object> list = (List<Object>) field.get(object);
//                for (int i = 0; i < lengthArray; i++) {
//                    Object element = Array.get(fieldValue, i);
//                    primitiveHandling(element);
//                }
//
//
//            if (field.getType()) {
//                for (int i = 0; i < lengthList; i++) {
//                    Object element = Array.get(fieldValue, i);
//                    primitiveHandling(element);
//                }
//            } else {
//                for (int i = 0; i < lengthList; i++) {
//                    Object element = Array.get(fieldValue, i);
//                    notPrimitivesHandling(element);
//                }
//            }

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
