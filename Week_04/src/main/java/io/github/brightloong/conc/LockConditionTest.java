package io.github.brightloong.conc;

import io.github.brightloong.task.FiboTask;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author BrightLoong
 * @date 2021/1/28 23:49
 * @description
 */
public class LockConditionTest {
    final Lock lock = new ReentrantLock(); // 条件变量：队列不满
    final Condition done = lock.newCondition();
    private Integer result;

    public static void main(String[] args) {
        long start=System.currentTimeMillis();
        LockConditionTest lockConditionTest = new LockConditionTest();
        new Thread(lockConditionTest::setResult).start();
        System.out.println("异步计算结果为：" + lockConditionTest.getResult());
        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
    }

    public void setResult() {
        try {
            lock.lock();
            result = FiboTask.sum();
            done.signalAll();
        } finally {
           lock.unlock();
        }
    }

    public  Integer getResult() {
        try {
            lock.lock();
            while (result == null) {
                try {
                    done.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return result;
        } finally {
            lock.unlock();
        }
    }
}
