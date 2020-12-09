package com.bjtu.redis.action;

import com.bjtu.redis.config.entity.Action;
import com.bjtu.redis.config.entity.Config;
import com.bjtu.redis.config.entity.Counter;
import com.bjtu.redis.counter.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author paul
 */
public class ActionService {
    private Action action;
    private Map<String, Object> values;

    public ActionService(String name, Map<String, Object> values) {
        this.values = values;
        action = Config.getInstance().getActions().getAction(name);
        if (action == null) {
            throw new RuntimeException("Wrong action name!");
        }
    }

    private BaseCounter judgeType(Counter counter) {
        switch (counter.getType()) {
            case "freq":
                return new Freq(counter, values);
            case "num":
                return new Num(counter, values);
            case "set":
                return new Set(counter, values);
            case "list":
                return new RedisList(counter, values);
            default:
                return null;
        }
    }

    public Map<String, Long> retrieve() {
        Map<String, String> map = action.getRetrieveCounter();
        Map<String, Long> result = new HashMap<>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            Counter counter = Config.getInstance().getCounters().getCounter(entry.getKey());
            BaseCounter baseCounter = judgeType(counter);
            if (baseCounter != null) {
                long retrieve = baseCounter.retrieve();
                result.put(entry.getKey(), retrieve);
            } else {
                throw new RuntimeException();
            }
        }
        return result;

    }

    public void save() {
        List<String> counters = action.getSaveCounter();
        for (String counterName : counters) {
            Counter counter = Config.getInstance().getCounters().getCounter(counterName);
            BaseCounter baseCounter = judgeType(counter);
            if (baseCounter != null) {
                baseCounter.save();
            }
        }
    }
}
