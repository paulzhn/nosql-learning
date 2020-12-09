package com.bjtu.redis.config.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * @author paul
 */
public class Actions {
    private Map<String, Action> actionMap;

    public Actions() {
        actionMap = new HashMap<>();
    }

    public void addAction(Action action) {
        actionMap.put(action.getName(), action);
    }

    public Action getAction(String name) {
        return actionMap.getOrDefault(name, null);
    }

    public Map<String, Action> getActionMap() {
        return actionMap;
    }


}

