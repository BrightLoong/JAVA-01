package io.github.brightloong.conc;

import io.github.brightloong.task.FiboTask;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author BrightLoong
 * @date 2021/1/28 22:14
 * @description
 */
public class FutureTaskTest {
    public static void main(String[] args) {
        FutureTask<Integer> futureTask = new FutureTask<>(FiboTask::sum);
        long start=System.currentTimeMillis();
        new Thread(futureTask).start();
        try {
            System.out.println("异步计算结果为：" + futureTask.get());
            System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
