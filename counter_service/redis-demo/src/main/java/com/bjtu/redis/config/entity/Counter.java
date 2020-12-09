package com.bjtu.redis.config.entity;

import com.alibaba.fastjson.JSONArray;

import java.util.List;
import java.util.Map;

/**
 * @author paul
 */
public class Counter {
    private String counterName;
    private String type;
    private String key;
    private String saveKey;
    private List<String> keyFields;
    private List<String> valueFields;
    private String periodField;
    private int counterIndex;
    private String redisCluster;
    /**
     * 0为不限制
     */
    private long maxValue;
    /**
     * 0为不限制
     */
    private int expireTime;

    public Counter() {

    }

    public Counter(String counterName, String type, String key, String saveKey, List<String> keyFields, List<String> valueFields, String periodField, int counterIndex, String redisCluster, long maxValue, int expireTime) {
        this.counterName = counterName;
        this.type = type;
        this.key = key;
        this.saveKey = saveKey;
        this.keyFields = keyFields;
        this.valueFields = valueFields;
        this.periodField = periodField;
        this.counterIndex = counterIndex;
        this.redisCluster = redisCluster;
        this.maxValue = maxValue;
        this.expireTime = expireTime;
    }

    public static Counter parseCounter(Map<String, Object> map) {
        String counterName = map.get("counterName").toString();
        String type = map.get("type").toString();
        String key = map.get("key").toString();
        String saveKey = map.getOrDefault("saveKey", "").toString();
        String periodField = map.getOrDefault("periodField", "").toString();
        int maxValue = (Integer) map.getOrDefault("maxValue", 0);
        List<String> valueFields = JSONArray.parseArray(map.getOrDefault("valueFields", "[]").toString(), String.class);
        String redisCluster = map.getOrDefault("redisCluster", "default").toString();
        List<String> keyFields = JSONArray.parseArray(map.get("keyFields").toString(), String.class);
        int counterIndex = Integer.parseInt(map.get("counterIndex").toString());
        int expireTime = (Integer) map.getOrDefault("expireTime", 0);

        return new Counter(counterName, type, key, saveKey, keyFields, valueFields, periodField, counterIndex, redisCluster, maxValue, expireTime);
    }

    public String getName() {
        return counterName;
    }

    public String getKey() {
        return key;
    }

    public List<String> getKeyFields() {
        return keyFields;
    }

    public List<String> getValueFields() {
        return valueFields;
    }

    public int getExpireTime() {
        return expireTime;
    }

    public String getSaveKey() {
        return saveKey;
    }

    public String getPeriodField() {
        return periodField;
    }

    public String getType() {
        return type;
    }
}