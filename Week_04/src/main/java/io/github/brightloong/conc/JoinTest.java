package io.github.brightloong.conc;

import io.github.brightloong.task.FiboTask;

/**
 * @author BrightLoong
 * @date 2021/1/28 23:13
 * @description
 */
public class JoinTest {
    private static int result;
    public static void main(String[] args) {
        long start=System.currentTimeMillis();
        Thread thread = new Thread(() -> {
            result = FiboTask.sum();
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("异步计算结果为：" + result);
        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
    }
}
