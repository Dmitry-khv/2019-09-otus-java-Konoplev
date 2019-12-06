package ru.otus.json;

import java.util.*;

public class Person {
    private final String firstName;
    private final String lastName;
    private int age;
    private final char aChar;
    private final boolean isMale;
    private List<String> phoneBook = new ArrayList<>();
    private int[] numbers;
    private String[] family;


    public Person(String firstName, String lastName, int age, char aChar, boolean isMale) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.aChar = aChar;
        this.isMale = isMale;
    }
    public void addToPhoneBook(String number) {
        phoneBook.add(number);
    }
    public void addRelatives(String...relative) {
        family = Arrays.copyOf(relative, relative.length);
    }
    public void addNumbers(int...number) {
        this.numbers = Arrays.copyOf(number, number.length);
    }

    @Override
    public String toString() {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", phoneBook=" + phoneBook +
                ", family=" + Arrays.toString(family) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return age == person.age &&
                Objects.equals(firstName, person.firstName) &&
                Objects.equals(lastName, person.lastName) &&
                Objects.equals(phoneBook, person.phoneBook) &&
                Arrays.equals(family, person.family);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(firstName, lastName, age, phoneBook);
        result = 31 * result + Arrays.hashCode(family);
        return result;
    }
}
