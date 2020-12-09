package com.bjtu.redis.counter;

import com.bjtu.redis.JedisInstance;
import com.bjtu.redis.config.entity.Counter;
import redis.clients.jedis.Jedis;

import java.util.Map;

/**
 * @author paul
 */
public class Freq extends BaseCounter {
    private String keyPattern, toKey;
    private String fromPeriod, toPeriod;
    private String saveKey;


    public Freq(Counter counter, Map<String, Object> values) {
        fromPeriod = values.getOrDefault("fromPeriod", "").toString();
        toPeriod = values.getOrDefault("toPeriod", "").toString();

        StringBuilder keyBuilder = new StringBuilder(counter.getKey());
        StringBuilder saveKeyBuilder = new StringBuilder(counter.getSaveKey());

        for (String keyField : counter.getKeyFields()) {
            keyBuilder.append("|").append(keyField).append("|");
            if (!keyField.equals(counter.getPeriodField())) {
                saveKeyBuilder.append("|").append(keyField).append("|");
                saveKeyBuilder.append(values.getOrDefault(keyField, ""));
            }

            if (keyField.equals(counter.getPeriodField())) {
                keyBuilder.append("%s");

            } else {
                keyBuilder.append(values.getOrDefault(keyField, ""));
            }
        }
        for (String valueField : counter.getValueFields()) {
            saveKeyBuilder.append("|").append(valueField).append("|");
            saveKeyBuilder.append(values.getOrDefault(valueField, ""));
        }

        keyPattern = keyBuilder.toString();
        saveKey = saveKeyBuilder.toString();
    }

    @Override
    public void save() {
        Jedis jedis = null;
        try {
            jedis = JedisInstance.getInstance().getResource();
            jedis.set(saveKey, Long.toString(retrieve()));
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
            long count = 0;
            int from = Integer.parseInt(fromPeriod);
            int to = Integer.parseInt(toPeriod);
            String type;
            for (int i = from; i <= to; i++) {
                String key = String.format(keyPattern, i);
                type = jedis.type(key);
                switch (type) {
                    case "num":
                        count += Long.parseLong(jedis.get(key));
                        break;
                    case "set":
                        count += jedis.scard(key);
                        break;
                    case "list":
                        count += jedis.llen(key);
                        break;
                    case "none":
                        continue;
                    default:
                        throw new RuntimeException("Unsupported type");
                }
            }
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
}
