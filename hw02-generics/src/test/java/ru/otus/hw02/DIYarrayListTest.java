package ru.otus.hw02;

import org.junit.jupiter.api.Test;

import java.util.Collections;

public class DIYarrayListTest {
    @Test
    void addAll() {
        DIYarrayList<Integer> list = new DIYarrayList<>(15);

        Collections.addAll(list, 1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31);
        for (int i = 0; i < list.size(); i++) {
            System.out.print(list.get(i) + " ");
        }
    }

    @Test
    void sort() {
        DIYarrayList<Character> list = new DIYarrayList<>();
        Collections.addAll(list, 'z', 'x', 'y', 'r', 't', 'd', 'f', 'g', 'e', 'p', 'l', 'q', 's', 'j', 'b', 'm', 'n', 'v', 'w', 'y', 'u', 'i');

//        DIYarrayList<Integer> list = new DIYarrayList<>();
//        Collections.addAll(list, 22,666,31,26,861,265,15,1,23,864,5,431,5,66,99,12,23,45,46,53,54,65,54,98,46,28,99,87,54,61);


        Collections.sort(list, null);

        for (int i = 0; i < list.size(); i++) {
            System.out.print(list.get(i) + " ");
        }
    }

    @Test
    void copy() {
//        ArrayList<Integer> src = new ArrayList<>();
//        Collections.addAll(src, 22,666,31,26,861,265,15,1,23,864,5,431,5,66,99,12,23,45,46,53,54,65,54,98,46,28,99,87,54,61);
//        ArrayList<Integer> dest = new ArrayList<>(src);

        DIYarrayList<Integer> src = new DIYarrayList<>();
        Collections.addAll(src, 22,666,31,26,861,265,15,1,23,864,5,431,5,66,99,12,23,45,46,53,54,65,54,98,46,28,99,87,54,61);
        DIYarrayList<Integer> dest = new DIYarrayList<>(src);

        Collections.copy(dest, src);

        for (int i = 0; i < src.size(); i++) {
            System.out.print(src.get(i) + " ");
        }
        System.out.println();

        for (int i = 0; i < dest.size(); i++) {
            System.out.print(dest.get(i) + " ");
        }

    }

}
