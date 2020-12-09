package com.bjtu.redis.config.entity;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;


/**
 * @author paul
 */
public class Action {
    private String name;
    private String desc;
    private Map<String, String> retrieveCounter;
    private List<String> saveCounter;

    public Action(String name, String desc, Map<String, String> retrieveCounter, List<String> saveCounter) {
        this.name = name;
        this.desc = desc;
        this.retrieveCounter = retrieveCounter;
        this.saveCounter = saveCounter;
    }

    public static Action parseAction(Map<String, Object> map) {
        String name = map.get("name").toString();
        String desc = map.get("desc").toString();
        String retrieveStr = map.get("retrieve").toString();
        Map<String, String> retrieveCounter = JSONObject.parseObject(retrieveStr).toJavaObject(Map.class);
        List<String> saveCounter = JSONArray.parseArray(map.getOrDefault("save", "[]").toString(), String.class);


        return new Action(name, desc, retrieveCounter, saveCounter);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Map<String, String> getRetrieveCounter() {
        return retrieveCounter;
    }

    public void setRetrieveCounter(Map<String, String> retrieveCounter) {
        this.retrieveCounter = retrieveCounter;
    }

    public List<String> getSaveCounter() {
        return saveCounter;
    }

    public void setSaveCounter(List<String> saveCounter) {
        this.saveCounter = saveCounter;
    }
}
