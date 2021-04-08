package io.github.brightloong.redis;

import redis.clients.jedis.Jedis;

import java.util.Collections;
import java.util.Objects;

/**
 * @author BrightLoong
 * @date 2021/4/8 15:29
 * @description 简易版本的redis计数器
 */
public class RedisCounter {

    private static final String DECR_LUA = "if (redis.call('exists', KEYS[1]) == 1) then" +
            "    local stock = tonumber(redis.call('get', KEYS[1]));" +
            "    local num = tonumber(ARGV[1]);" +
            "    if (stock >= num) then" +
            "        return redis.call('incrby', KEYS[1], 0 - num);" +
            "    end;" +
            "    return -1;" +
            "end;" +
            "return -2;";

    //应该优化成池对象
    private Jedis jedis;

    private String key;

    public RedisCounter(String key, Jedis jedis, Long count) {
        this.jedis = jedis;
        this.key = key;
        //初始化count,存在不覆盖
        if (Objects.nonNull(count)) {
            jedis.setnx(key, String.valueOf(count));
        }
    }

    /**
     * 扣减库存
     * <p>-2:库存未初始化</p>
     * <p>-1:库存不足</p>
     * <p>大于等于0:剩余库存（扣减之后剩余的库存）</p>
     *
     * @param count
     * @return
     */
    public Long decr(Long count) {
        return (Long) jedis.eval(DECR_LUA, Collections.singletonList(this.key), Collections.singletonList(String.valueOf(count)));
    }
}
