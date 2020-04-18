package ru.otus.test;

import ru.otus.api.annotation.After;
import ru.otus.api.annotation.Before;
import ru.otus.api.annotation.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TestFramework {
    private static List<Method> beforeMethods = new ArrayList<>();
    private static List<Method> afterMethods = new ArrayList<>();
    private static List<Method> testMethods = new ArrayList<>();
    private static int testFalse;

    public static void main(String[] args) {
        String className = getTestedClassName();

        try {
            testLauncher(className);
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private static String getTestedClassName() {
        Class<TestClass> testedClass = TestClass.class;
        return testedClass.getName();
    }

    private static void testLauncher(String className) throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException {

        Class<?> testedClass = Class.forName(className);
        Method[] methods = testedClass.getDeclaredMethods();

        putMethodsIntoArrays(methods);

        for (Method testMethod : testMethods) {
            TestClass testClass = (TestClass) testedClass.newInstance();

            for (Method beforeMethod : beforeMethods)
                beforeMethod.invoke(testClass);
            try {
                testMethod.invoke(testClass);
            } catch (Exception e) {
                testFalse++;
            }

            for (Method afterMethod : afterMethods)
                afterMethod.invoke(testClass);
        }

        System.out.println("\nTested method: " + testMethods.size());
        System.out.println("Successful tested method: " + (testMethods.size() - testFalse));
        System.out.println("False tested method: " + testFalse);
    }

    private static void putMethodsIntoArrays(Method[] methods) {
        for (Method method : methods) {
            if(method.isAnnotationPresent(Before.class))
                beforeMethods.add(method);
            else if(method.isAnnotationPresent(After.class))
                afterMethods.add(method);
            else if(method.isAnnotationPresent(Test.class))
                testMethods.add(method);
        }
    }
}
