package ru.otus.aop.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MyInvocationHandler implements InvocationHandler {
    private final TestLoggingInterface loggingInterface;

    public MyInvocationHandler(TestLoggingInterface loggingInterface) {
        this.loggingInterface = loggingInterface;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("executed method: " + method.getName() + ", param: " + args[0]);
        return method.invoke(loggingInterface, args);
    }


}
