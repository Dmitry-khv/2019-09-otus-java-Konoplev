package ru.otus.aop.proxy;


public class ProxyTest {

    public static void main(String[] args) {
        TestLoggingInterface loggingInterface = AnnotationParser.getInstance();
        loggingInterface.calculate(777);
        loggingInterface.calculate(777, 666);
        loggingInterface.setInt(6);
    }
}
