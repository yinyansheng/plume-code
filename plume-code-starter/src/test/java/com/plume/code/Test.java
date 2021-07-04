package com.plume.code;

import lombok.SneakyThrows;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Test {

    private static Object object = new Object();
    private static volatile Integer times = 0;

    public static void main(String[] args) throws IOException {
        print(0, "A" );
        print(1, "B" );
        print(2, "C" );

        System.in.read();
    }

    public static void print(Integer i, String c) {
        Executors.newFixedThreadPool(1).execute(() -> {
            while (times < 20) {
                synchronized (object) {
                    if (times % 3 == i) {
                        System.out.printf(c);
                        times++;
                    }
                    object.notifyAll();
                    try {
                        object.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

}
