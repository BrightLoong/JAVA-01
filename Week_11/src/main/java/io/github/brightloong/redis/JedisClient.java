package io.github.brightloong.redis;

import redis.clients.jedis.Jedis;

import java.util.Objects;

/**
 * @author BrightLoong
 * @date 2021/4/7 17:23
 * @description
 */
public class JedisClient {

    private final Jedis jedis;

    public JedisClient(String host, int port, String password) {
        this.jedis = new Jedis(host, port);
        if (Objects.nonNull(password)) {
            this.jedis.auth(password);
        }
    }

    public RedisLock getLock(String key) {
        return new RedisLock(this.jedis, key);
    }

    public RedisCounter getCounter(String key, Long count) {
        return new RedisCounter(key, this.jedis, count);
    }
}
