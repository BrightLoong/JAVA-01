package io.github.brightloong.conc;

import io.github.brightloong.task.FiboTask;

/**
 * @author BrightLoong
 * @date 2021/1/28 23:17
 * @description
 */
public class WaitAndNotifyTest {

    private Integer result;

    public static void main(String[] args) {
        long start=System.currentTimeMillis();
        WaitAndNotifyTest waitAndNotifyTest = new WaitAndNotifyTest();
        new Thread(waitAndNotifyTest::setResult).start();
        System.out.println("异步计算结果为：" + waitAndNotifyTest.getResult());
        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
    }

    public synchronized void setResult() {
        result = FiboTask.sum();
        notifyAll();
    }

    public synchronized Integer getResult() {
        while (result == null) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
