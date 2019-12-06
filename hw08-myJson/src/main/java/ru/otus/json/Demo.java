package ru.otus.json;

import com.google.gson.Gson;

public class Demo {
    public static void main(String[] args) throws IllegalAccessException {
        Person person = new Person("Steve", "Johnson", 29, 'H', true);
        person.addToPhoneBook("8-962-222-22-22");
        person.addToPhoneBook("8-495-485-22-99");
        person.addRelatives("mom", "dad", "dog", "cat");
        person.addNumbers(14, 15, 2008);
        System.out.println(person);

        MyJson myJson = new MyJson();
        String testPerson = myJson.toJson(person);
        System.out.println(testPerson);

        Gson gson = new Gson();
        String person1 = gson.toJson(person, Person.class);
        System.out.println(person1);

//        System.out.println(person.equals(person1));

    }
}
