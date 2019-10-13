package ru.otus.hw02;


import org.junit.jupiter.api.Test;

import java.util.Collections;

public class DIYarrayTest {
    @Test
    void addAll() {
        DIYarrayList<Integer> list = new DIYarrayList<>();

        Collections.addAll(list, 1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30);
    }

    @Test
    void copy() {
        DIYarrayList<Integer> list = new DIYarrayList<>();

        Collections.sort(list, null);

    }

}
