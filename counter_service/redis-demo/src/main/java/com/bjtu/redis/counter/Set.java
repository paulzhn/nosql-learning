package com.bjtu.redis.counter;

import com.bjtu.redis.JedisInstance;
import com.bjtu.redis.config.entity.Counter;
import redis.clients.jedis.Jedis;

import java.util.Map;

/**
 * @author paul
 */
public class Set extends BaseCounter {

    public Set(Counter counter, Map<String, Object> values) {
        super(counter, values);
    }

    @Override
    public void save() {
        addMember();
    }

    @Override
    public long retrieve() {
        return getCount();
    }

    private long getCount() {
        Jedis jedis = null;
        try {
            jedis = JedisInstance.getInstance().getResource();
            long count = jedis.scard(key);
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    private void addMember() {
        Jedis jedis = null;
        try {
            jedis = JedisInstance.getInstance().getResource();
            for (String valueField : valueFields) {
                jedis.sadd(key, valueField);
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
}
