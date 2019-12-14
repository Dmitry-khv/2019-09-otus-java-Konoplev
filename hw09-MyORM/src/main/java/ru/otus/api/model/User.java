package ru.otus.api.model;

import ru.otus.api.annotations.Id;

public class User {
    @Id
    private final int id;
    private final String name;
    private int age;

    public User(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name +
                "' , age=" + age + "}";
    }
}
