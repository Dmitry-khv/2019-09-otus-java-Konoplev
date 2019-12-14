package ru.otus.json;

import com.google.gson.Gson;

import java.util.Collections;
import java.util.List;

public class Demo {
    public static void main(String[] args) {
        Person person = new Person("Steve", "Johnson", 29, 'H', true);
        person.addToPhoneBook("8-962-222-22-22");
        person.addToPhoneBook("8-495-485-22-99");
        person.addRelatives("mom", "dad", "dog", "cat");
        person.addNumbers(14, 15, 2008);
        person.addCar(new Car("lamborghini", 2019), new Car("audi", 2019));

        MyJson myJson = new MyJson();
        String testPerson = myJson.toJson(person);
        System.out.println(testPerson);

        Gson gson = new Gson();
        String person1 = gson.toJson(person);
        System.out.println(person1);
        Person personDolly = gson.fromJson(testPerson, Person.class);

        System.out.println(person.equals(personDolly));
        System.out.println(person);
        System.out.println(personDolly);
        System.out.println();
        System.out.println(myJson.toJson(null));
        System.out.println(gson.toJson(null));

        System.out.println(myJson.toJson((byte)1));
        System.out.println(gson.toJson((byte)1));

        System.out.println(myJson.toJson((short)1f));
        System.out.println(gson.toJson((short)1f));

        System.out.println(myJson.toJson(1));
        System.out.println(gson.toJson(1));

        System.out.println(myJson.toJson(1L));
        System.out.println(gson.toJson(1L));

        System.out.println(myJson.toJson(1f));
        System.out.println(gson.toJson(1f));

        System.out.println(myJson.toJson(1d));
        System.out.println(gson.toJson(1d));

        System.out.println(myJson.toJson("aaa"));
        System.out.println(gson.toJson("aaa"));


        System.out.println(myJson.toJson('a'));
        System.out.println(gson.toJson('a'));

        System.out.println(myJson.toJson(new int[] {1, 2, 3}));
        System.out.println(gson.toJson(new int[] {1, 2, 3}));

        System.out.println(myJson.toJson(List.of(1, 2 ,3)));
        System.out.println(gson.toJson(List.of(1, 2 ,3)));

        System.out.println(myJson.toJson(Collections.singletonList(1)));
        System.out.println(gson.toJson(Collections.singletonList(1)));


    }
}
