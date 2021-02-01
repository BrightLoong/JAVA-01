package io.github.brightloong.conc;

import io.github.brightloong.task.FiboTask;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author BrightLoong
 * @date 2021/1/29 09:40
 * @description
 */
public class BlockingQueueTest {
    private static final BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(1);
    public static void main(String[] args) {
        long start=System.currentTimeMillis();
        new Thread(() -> {
            queue.offer(FiboTask.sum());
        }).start();
        try {
            System.out.println("异步计算结果为：" + queue.take());
            System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
