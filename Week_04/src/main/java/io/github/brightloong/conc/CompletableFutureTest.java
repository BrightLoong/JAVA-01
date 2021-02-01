package io.github.brightloong.conc;

import io.github.brightloong.task.FiboTask;

import java.util.concurrent.CompletableFuture;

/**
 * @author BrightLoong
 * @date 2021/1/28 22:19
 * @description
 */
public class CompletableFutureTest {
    public static void main(String[] args) {
        long start=System.currentTimeMillis();
        CompletableFuture.supplyAsync(FiboTask::sum).thenAccept(result ->  System.out.println("异步计算结果为：" + result));
        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
    }
}
