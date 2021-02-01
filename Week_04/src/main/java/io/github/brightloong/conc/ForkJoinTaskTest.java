package io.github.brightloong.conc;

import io.github.brightloong.task.FiboTask;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

/**
 * @author BrightLoong
 * @date 2021/1/29 11:43
 * @description
 */
public class ForkJoinTaskTest {
    public static void main(String[] args) {
        long start=System.currentTimeMillis();
        ForkJoinPool forkJoinPool = new ForkJoinPool(1);
        ForkJoinTask<Integer> forkJoinTask = forkJoinPool.submit(FiboTask::sum);
        try {
            System.out.println("异步计算结果为：" + forkJoinTask.get());
            System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
