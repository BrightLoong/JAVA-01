package io.github.brightloong.conc;

import io.github.brightloong.task.FiboTask;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author BrightLoong
 * @date 2021/1/29 10:15
 * @description
 */
public class ExecutorServiceTerminationTest {
    private static int result;
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            result = FiboTask.sum();
        });
        //提交任务后开始关闭
        executorService.shutdown();
        try {
            boolean termination = false;
            while (!termination) {
                //等待关闭完成,预估不会超过1秒，使用了循环保证一定关闭
                termination = executorService.awaitTermination(1, TimeUnit.SECONDS);
            }
            System.out.println("异步计算结果为：" + result);
            System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
