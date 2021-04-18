package io.github.brightloong.cache.redis;

import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.redisson.config.ReadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * @author BrightLoong
 * @date 2021/4/7 10:12
 * @description
 */
public class RedisDemo {
    public static void main(String[] args) {
        //主从
        //RedissonClient redissonClient = masterSlaveClient();
        //哨兵
        //RedissonClient redissonClient = sentinelSlaveClient();
        //集群
        RedissonClient redissonClient = clusterSlaveClient();
        RBucket<String> bucket = redissonClient.getBucket("redis-test");
        bucket.set("hello redis",5, TimeUnit.SECONDS);
        System.out.println(bucket.get());
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(bucket.get());
        new ArrayList<>();
    }

    private static RedissonClient clusterSlaveClient() {
        //集群模式
        Config config = new Config();
        config.useClusterServers()
                .addNodeAddress("redis://127.0.0.1:6379","redis://127.0.0.1:6380","redis://127.0.0.1:6381");
        return Redisson.create(config);
    }

    private static RedissonClient sentinelSlaveClient() {
        //哨兵模式
        Config config = new Config();
        config.useSentinelServers()
                .setMasterName("mymaster")
                .addSentinelAddress("redis://127.0.0.1:26379", "redis://127.0.0.1:26380")
                .setReadMode(ReadMode.SLAVE);
        return Redisson.create(config);
    }

    private static RedissonClient masterSlaveClient() {
        //主从模式
        Config config = new Config();
        config.setCodec(JsonJacksonCodec.INSTANCE)
                .useMasterSlaveServers()
                .setMasterAddress("redis://127.0.0.1:6379")
                .addSlaveAddress("redis://127.0.0.1:6380")
                .setReadMode(ReadMode.SLAVE);

        return Redisson.create(config);
    }
}
