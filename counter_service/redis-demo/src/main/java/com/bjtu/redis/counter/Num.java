package com.bjtu.redis.counter;

import com.bjtu.redis.JedisInstance;
import com.bjtu.redis.config.entity.Counter;
import redis.clients.jedis.Jedis;

import java.util.Map;

/**
 * @author paul
 */
public class Num extends BaseCounter {
    public Num(Counter counter, Map<String, Object> values) {
        super(counter, values);
    }

    @Override
    public void save() {
        try {
            Jedis jedis = JedisInstance.getInstance().getResource();
            for (String valueField : valueFields) {
                long valueToIncr = Long.parseLong(valueField);
                jedis.incrBy(key, valueToIncr);
            }
            if (isNeedExpire()) {
                jedis.expire(key, expireTime);
            }
            jedis.close();

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public long retrieve() {
        Jedis jedis = JedisInstance.getInstance().getResource();
        String originResult = jedis.get(key);
        if (originResult == null) {
            throw new RuntimeException("Retrieve error, key not exist");
        }
        long result = Long.parseLong(originResult);
        jedis.close();
        return result;
    }
}
