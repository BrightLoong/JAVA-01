package io.github.brightloong.redis;

import redis.clients.jedis.Jedis;

import java.util.Collections;
import java.util.UUID;

/**
 * @author BrightLoong
 * @date 2021/4/7 15:43
 * @description 简易版本的redis分布式锁
 */
public class RedisLock {

    //应该优化成池对象
    private final Jedis jedis;

    private final String key;

    private final String randomValue;


    public RedisLock(Jedis jedis, String key) {
        this.jedis = jedis;
        this.key = key;
        this.randomValue = UUID.randomUUID().toString();
    }

    public boolean tryLock() {
        //默认锁定30秒
        String result = jedis.set(this.key, getFinalRandomValue(), "NX", "EX", 30);
        return "OK".equals(result);
    }

    public void unlock() {
        Long result = (Long) jedis.eval("if redis.call('get',KEYS[1]) == ARGV[1] then return redis.call('del',KEYS[1]); else return 0; end; ", Collections.singletonList(this.key), Collections.singletonList(this.getFinalRandomValue()));
        if ( 0L == result) {
            throw new RuntimeException("the lock not held by current thread");
        }
    }

    private String getFinalRandomValue() {
        return Thread.currentThread().getId() + "_" + randomValue;
    }
}
