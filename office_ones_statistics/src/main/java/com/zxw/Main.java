package com.zxw;

import java.util.Scanner;
public class Main {

    private static volatile int cur=0;
    private static final int Count=10;

    public static void main(String[] args) {
        Thread t1=new Thread(()->{
            while(cur<Count){
                synchronized(Main.class){
                    if(cur%2==0){
                        System.out.println("Thread1"+0);
                        cur++;
                    }
                }
            }
        });
        Thread t2=new Thread(()->{
            while(cur<Count){
                synchronized(Main.class){
                    if(cur%2!=0){
                        System.out.println("Thread2"+1);
                        cur++;
                    }
                }
            }
        });

        t1.start();
        t2.start();



    }
}