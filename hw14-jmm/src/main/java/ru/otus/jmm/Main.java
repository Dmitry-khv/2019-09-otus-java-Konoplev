package ru.otus.jmm;


public class Main {
    private static volatile int i = 0;
    private static volatile boolean isDisturbed = false;
    private static volatile boolean setUpCount = true;
    private static volatile boolean isChanged = true;

    public static void main(String[] args) {
        new Main().initThreads();
    }

    public void initThreads() {
        Thread thread1 = new Thread(this::launchFirstThread);
        Thread thread2 = new Thread(this::launchSecondThread);
        thread1.start();
        thread2.start();
    }

    public void launchFirstThread() {

        while (true) {
            if(!isDisturbed) {
                makeStep();
                isDisturbed = true;
            }

            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void launchSecondThread() {
        while (true) {
            if (isDisturbed) {
                makeStep();
                isDisturbed = false;
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public synchronized void makeStep() {
        if(setUpCount) {
            System.out.println(Thread.currentThread().getName() + " " + i);
            if(!isChanged) {
                i++;
                isChanged = true;
            } else {
                isChanged = false;
            }
            if(i > 9)
                setUpCount = false;
        } else {
            System.out.println(Thread.currentThread().getName() + " " + i);
            if(!isChanged) {
                i--;
                isChanged = true;
            } else {
                isChanged = false;
            }
            if(i < 1)
                setUpCount = true;
        }
    }
}
