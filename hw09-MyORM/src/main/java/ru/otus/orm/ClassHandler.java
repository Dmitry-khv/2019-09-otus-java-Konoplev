package ru.otus.orm;

import ru.otus.api.annotations.Id;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ClassHandler<K, V> {
    private final Object object;
    private Field[] fields;
    private static final Class ID_ANNOTATION = Id.class;
    private final List<Object> paramList = new ArrayList<>();

    public ClassHandler(Object object) {
        this.object = object;
    }

    public void generateQuery() {
        if (checkAnnotation()) {

        }
    }

    private boolean checkAnnotation() {
        fields = object.getClass().getFields();
        boolean isAnnotated = false;

        for(Field field : fields) {
            if (field.isAnnotationPresent(ID_ANNOTATION))
                isAnnotated = true;
        }
        return isAnnotated;
    }

    public void getParamList() {

    }
}
