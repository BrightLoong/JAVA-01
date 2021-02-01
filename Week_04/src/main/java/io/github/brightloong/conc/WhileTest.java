package io.github.brightloong.conc;

import io.github.brightloong.task.FiboTask;

/**
 * @author BrightLoong
 * @date 2021/1/28 23:29
 * @description
 */
public class WhileTest {
    private static int result;
    private static volatile boolean done = false;

    public static void main(String[] args) {
        long start=System.currentTimeMillis();
        new Thread(() -> {
            result = FiboTask.sum();
            //volatile可见性，加传递性
            done = true;
        }).start();
        while (!done) {

        }
        System.out.println("异步计算结果为：" + result);
        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
    }
}
