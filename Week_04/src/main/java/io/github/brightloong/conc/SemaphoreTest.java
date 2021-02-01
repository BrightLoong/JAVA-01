package io.github.brightloong.conc;

import io.github.brightloong.task.FiboTask;

import java.util.concurrent.Semaphore;

/**
 * @author BrightLoong
 * @date 2021/1/29 10:00
 * @description
 */
public class SemaphoreTest {
    private static int result;
    public static void main(String[] args) {
        long start=System.currentTimeMillis();
        Semaphore done = new Semaphore(0);
        new Thread(() -> {
            result = FiboTask.sum();
            done.release();
        }).start();

        try {
            done.acquire();
            System.out.println("异步计算结果为：" + result);
            System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
