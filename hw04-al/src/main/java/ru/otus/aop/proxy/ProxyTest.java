package ru.otus.aop.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class ProxyTest {
    public static void main(String[] args) {
        InvocationHandler handler = new MyInvocationHandler(new TestLogging());
        TestLoggingInterface loggingInterface = (TestLoggingInterface) Proxy.newProxyInstance(ProxyTest.class.getClassLoader(),
                new Class<?>[] {TestLoggingInterface.class}, handler);
        loggingInterface.calculate(777);
    }
}
