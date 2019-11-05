package ru.otus.aop.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

public class AnnotationParser {

    public static TestLoggingInterface getInstance() {
        InvocationHandler handler = new MyInvocationHandler(new TestLogging());
        return (TestLoggingInterface) Proxy.newProxyInstance(AnnotationParser.class.getClassLoader(),
                new Class<?>[]{TestLoggingInterface.class}, handler);
    }

    static class MyInvocationHandler implements InvocationHandler {
        private final TestLoggingInterface loggingInterface;

        private List<String> methodsName = new ArrayList<>();
        private StringBuilder annotatedMethod;
        private Parameter[] methodParameters;
        private Class desiredAnnotation = Log.class;

        public void getAnnotatedMethod() {
            Method[] methods = loggingInterface.getClass().getDeclaredMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(desiredAnnotation)) {
                    annotatedMethod = new StringBuilder();
                    annotatedMethod.append(method.getName());
                    methodParameters = method.getParameters();
                    for (int i = 0; i < methodParameters.length; i++)
                        annotatedMethod.append(",").append(methodParameters[i]);
                    methodsName.add(annotatedMethod.toString());
                }
            }
        }

        public MyInvocationHandler(TestLogging loggingInterface) {
            this.loggingInterface = loggingInterface;
            getAnnotatedMethod();
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (isOccurMethod(method))
                printMethodName(method, args);
            return method.invoke(loggingInterface, args);
        }

        private boolean isOccurMethod(Method method) {
            boolean isOccur = false;
            annotatedMethod = new StringBuilder(method.getName());
            methodParameters = method.getParameters();

            for (int i = 0; i < methodParameters.length; i++)
                annotatedMethod.append(",").append(methodParameters[i]);

            if (methodsName.contains(annotatedMethod.toString()))
                isOccur = true;

            return isOccur;
        }

        private void printMethodName(Method method, Object[] args) {
            System.out.print("executed method: " + method.getName());
            for (int i = 0; i < args.length; i++) {
                System.out.print(", param" + (i + 1) + ": " + args[i]);
            }
            System.out.println("");
        }
    }
}
