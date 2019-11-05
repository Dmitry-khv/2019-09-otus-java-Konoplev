package ru.otus.aop.proxy;

public class TestLogging implements TestLoggingInterface {

    @Log
    public void calculate(int param) {
    }

    @Log
    public void calculate(int param1, int param2) {
    }

    public void setInt(int i) {
    }
}
