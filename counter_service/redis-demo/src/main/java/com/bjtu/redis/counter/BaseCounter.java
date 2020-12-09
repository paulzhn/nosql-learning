package com.bjtu.redis.counter;

import com.bjtu.redis.config.entity.Counter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author paul
 */
public abstract class BaseCounter {
    private List<String> keyFields;
    List<String> valueFields;
    String key;
    int expireTime;

    BaseCounter() {

    }

    BaseCounter(Counter counter, Map<String, Object> values) {
        keyFields = new ArrayList<>();
        valueFields = new ArrayList<>();
        StringBuilder key = new StringBuilder(counter.getKey());
        for (String keyField : counter.getKeyFields()) {
            key.append("|").append(keyField).append("|");
            String value = values.getOrDefault(keyField, "").toString();
            key.append(value);
        }
        this.key = key.toString();
        for (String valueField : counter.getValueFields()) {
            valueFields.add(values.getOrDefault(valueField, "").toString());
        }
        expireTime = counter.getExpireTime();
    }

    boolean isNeedExpire() {
        return expireTime != 0;
    }

    public abstract void save();

    public abstract long retrieve();
}
