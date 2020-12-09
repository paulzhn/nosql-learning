package com.bjtu.redis.counter;

import com.bjtu.redis.JedisInstance;
import com.bjtu.redis.config.entity.Counter;
import redis.clients.jedis.Jedis;

import java.util.Map;

/**
 * @author bytedance
 */
public class RedisList extends BaseCounter {

    public RedisList(Counter counter, Map<String, Object> values) {
        super(counter, values);
    }

    @Override
    public void save() {
        Jedis jedis = null;
        try {
            jedis = JedisInstance.getInstance().getResource();
            for (String valueField : valueFields) {
                jedis.lpush(key, valueField);
            }
            if (isNeedExpire()) {
                jedis.expire(key, expireTime);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    @Override
    public long retrieve() {
        Jedis jedis = null;
        try {
            jedis = JedisInstance.getInstance().getResource();
            return jedis.llen(key);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }
}
