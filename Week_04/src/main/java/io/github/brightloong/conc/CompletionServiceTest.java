package io.github.brightloong.conc;

import io.github.brightloong.task.FiboTask;

import java.util.concurrent.*;

/**
 * @author BrightLoong
 * @date 2021/1/28 22:25
 * @description
 */
public class CompletionServiceTest {
    public static void main(String[] args) {
        long start=System.currentTimeMillis();
        ExecutorService executor = Executors.newSingleThreadExecutor();
        ExecutorCompletionService<Integer> executorCompletionService = new ExecutorCompletionService<>(executor);
        executorCompletionService.submit(FiboTask::sum);
        try {
            System.out.println("异步计算结果为：" + executorCompletionService.take().get());
            System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
            executor.shutdown();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
