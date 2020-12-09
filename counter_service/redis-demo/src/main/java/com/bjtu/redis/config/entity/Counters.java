package com.bjtu.redis.config.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author paul
 */
public class Counters {
    private List<Counter> counters;
    private Map<String, Counter> counterMap;

    public Counters() {
        counters = new ArrayList<>();
        counterMap = new HashMap<>();
    }

    public Map<String, Counter> getCounterMap() {
        return counterMap;
    }

    public void addCounter(Counter counter) {
        counters.add(counter);
        counterMap.put(counter.getName(), counter);
    }

    public Counter getCounter(String name) {
        return counterMap.getOrDefault(name, null);
    }


}
