package io.github.brightloong.conc;

import io.github.brightloong.task.FiboTask;

import java.util.concurrent.CountDownLatch;

/**
 * @author BrightLoong
 * @date 2021/1/28 22:35
 * @description
 */
public class CountDownLatchTest {
    private static volatile int result;
    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(1);
        long start=System.currentTimeMillis();
        new Thread(() -> {
            result = FiboTask.sum();
            latch.countDown();
        }).start();
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("异步计算结果为：" + result);
        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
    }
}
