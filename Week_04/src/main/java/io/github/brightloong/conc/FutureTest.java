package io.github.brightloong.conc;

import io.github.brightloong.task.FiboTask;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author BrightLoong
 * @date 2021/1/28 23:37
 * @description
 */
public class FutureTest {
    public static void main(String[] args) {
        long start=System.currentTimeMillis();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<Integer> future = executorService.submit(FiboTask::sum);
        try {
            System.out.println("异步计算结果为：" + future.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
        executorService.shutdown();
    }
}
