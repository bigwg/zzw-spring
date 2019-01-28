package com.zzw.trta.thread;

import lombok.Getter;
import lombok.Setter;

/**
 * @author zhaozhiwei
 * @description
 * @date 2018/9/8 上午11:10
 */
@Getter
@Setter
public class VolatileTest {

    private volatile boolean condition = true;
    private String str = "";

    public static void main(String[] args) throws InterruptedException {
        for (int i=0;i<100;i++){
            VolatileTest volatileTest = new VolatileTest();
    //        new Thread(new ReadRunnable(volatileTest)).start();
    //        Thread.sleep(1);
            new Thread(new WriteRunnable(volatileTest)).start();
//            Thread.sleep(200);
            new Thread(new ReadRunnable(volatileTest)).start();
            Thread.sleep(200);
            System.out.println("-----------------------");
        }
    }
}

class WriteRunnable implements Runnable {

    private VolatileTest volatileTest;

    public WriteRunnable(VolatileTest volatileTest) {
        this.volatileTest = volatileTest;
    }

    @Override
    public void run() {
        volatileTest.setStr("write...");
//        try {
//            Thread.sleep(1);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        System.out.println("WriteRunnable:" + volatileTest.getStr());
    }
}

class ReadRunnable implements Runnable {

    private VolatileTest volatileTest;

    public ReadRunnable(VolatileTest volatileTest) {
        this.volatileTest = volatileTest;
    }

    @Override
    public void run() {
        volatileTest.setStr("read...");
        System.out.println("ReadRunnable:" + volatileTest.getStr());
    }
}
