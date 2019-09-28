package main.java;

import com.google.common.base.Joiner;

import java.util.ArrayList;

public class HelloOtus {
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 1; i < 100; i++) {
            list.add(String.valueOf(i));
        }
        String joiner = Joiner.on(") ").skipNulls().join(list);
        System.out.println(joiner);
    }
}
