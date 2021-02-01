package io.github.brightloong.conc;

import io.github.brightloong.task.FiboTask;

import java.util.concurrent.Exchanger;

/**
 * @author BrightLoong
 * @date 2021/1/29 11:34
 * @description
 */
public class ExchangerTest {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        Exchanger<Integer> exchanger = new Exchanger<>();
        new Thread(() -> {
            try {
                exchanger.exchange(FiboTask.sum());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        try {
            System.out.println("异步计算结果为：" + exchanger.exchange(0));
            System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
