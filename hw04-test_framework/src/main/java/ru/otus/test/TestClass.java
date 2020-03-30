package ru.otus.test;

import ru.otus.api.annotation.After;
import ru.otus.api.annotation.Before;
import ru.otus.api.annotation.Test;

public class TestClass {

    @Before
    public void beforeMethod1() {
        System.out.println("Starting test...");
    }

    @Before
    public void beforeMethod2() {
        System.out.println("Starting test...");
    }

    @Test
    public void testMethod1() {
        System.out.println("Testing...1");
        int v = 1/0;
    }

    @Test
    public void testMethod2() {
        System.out.println("Testing...2");
    }

    @After
    public void afterMethod() {
        System.out.println("Test has ended!");
    }
}