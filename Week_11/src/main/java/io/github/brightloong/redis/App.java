package io.github.brightloong.redis;

import java.util.concurrent.TimeUnit;

/**
 * @author BrightLoong
 * @date 2021/4/7 17:55
 * @description
 */
public class App {
    public static void main(String[] args) {
        //测试分布式锁
        //testForLock();
        //测试计数器
        testForCounter();
    }

    private static void testForCounter() {
        for (int i = 0; i < 13; i++) {
            JedisClient jedisClient = new JedisClient("127.0.0.1", 6379, null);
            RedisCounter counter = jedisClient.getCounter("counter-test", 10L);
            new Thread(() -> {
                Long decr = counter.decr(1L);
                if (decr == -2) {
                    System.out.println("库存未初始化");
                } else if (decr == -1) {
                    System.out.println("库存不足");
                } else {
                    System.out.println("库存扣减成功，当前剩余：" + decr);
                }
            }, "thread-" + i).start();
        }
    }

    private static void testForLock() {
        for (int i = 0; i < 50; i++) {
            JedisClient jedisClient = new JedisClient("127.0.0.1", 6379, null);
            RedisLock lock = jedisClient.getLock("lock-test");
            new Thread(() -> {
                if (lock.tryLock()) {
                    try {
                        //do something
                        System.out.println(Thread.currentThread().getName() + "：lock success");
                        TimeUnit.SECONDS.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        lock.unlock();
                    }
                } else {
                    System.out.println(Thread.currentThread().getName() + "：lock fail");
                }
            }, "thread-" + i).start();
        }
    }
}
