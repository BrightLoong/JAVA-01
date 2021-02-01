package io.github.brightloong.conc;

import io.github.brightloong.task.FiboTask;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author BrightLoong
 * @date 2021/1/28 23:01
 * @description
 */
public class CyclicBarrierTest {

    private static volatile int result;

    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2);
        long start = System.currentTimeMillis();
        new Thread(() -> {
            result = FiboTask.sum();
            try {
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }).start();
        try {
            cyclicBarrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
        System.out.println("异步计算结果为：" + result);
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
    }
}
