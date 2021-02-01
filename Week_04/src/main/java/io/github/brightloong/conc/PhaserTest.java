package io.github.brightloong.conc;

import io.github.brightloong.task.FiboTask;

import java.util.concurrent.Phaser;

/**
 * @author BrightLoong
 * @date 2021/1/29 10:55
 * @description
 */
public class PhaserTest {
    private static int result;
    public static void main(String[] args) {
        long start=System.currentTimeMillis();
        //1注册一个线程
        Phaser phaser = new Phaser(1);
        //注册子线程
        phaser.register();
        new Thread(() -> {
            result = FiboTask.sum();
            phaser.arriveAndAwaitAdvance();
        }).start();
        phaser.arriveAndAwaitAdvance();
        System.out.println("异步计算结果为：" + result);
        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
    }
}
