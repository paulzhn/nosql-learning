package com.bjtu.redis.config;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONReader;
import com.bjtu.redis.config.entity.*;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import static com.bjtu.redis.Const.ACTION_WATCHER;
import static com.bjtu.redis.Const.COUNTER_WATCHER;

/**
 * @author paul
 */
public class ConfigParser {
    private ConfigParser() {
        loadConfig(ACTION_WATCHER);
        loadConfig(COUNTER_WATCHER);
        FileWatcher watcher = new FileWatcher();
        watcher.start();
    }

    public static ConfigParser getInstance() {
        return SingletonEnum.INSTANCE.parser;
    }

    public Actions getActions() {
        return Config.getInstance().getActions();
    }

    public Counters getCounters() {
        return Config.getInstance().getCounters();
    }

    void loadConfig(int type) {
        if (type == ACTION_WATCHER) {
            InputStream stream = ConfigParser.class.getResourceAsStream("/actions.json");
            JSONReader reader = new JSONReader(new InputStreamReader(stream));
            reader.startObject();

            Actions actions = new Actions();
            Config.getInstance().setActions(actions);
            if (reader.hasNext() && "actions".equals(reader.readString())) {
                reader.startArray();
                while (reader.hasNext()) {
                    String str = reader.readObject().toString();
                    Map<String, Object> object = JSONObject.parseObject(str).toJavaObject(Map.class);
                    Action action = Action.parseAction(object);
                    actions.addAction(action);
                }
            }

        } else if (type == COUNTER_WATCHER) {
            InputStream stream = ConfigParser.class.getResourceAsStream("/counters.json");
            JSONReader reader = new JSONReader(new InputStreamReader(stream));
            reader.startObject();

            Counters counters = new Counters();
            Config.getInstance().setCounters(counters);
            if (reader.hasNext() && "counters".equals(reader.readString())) {
                reader.startArray();
                while (reader.hasNext()) {
                    String str = reader.readObject().toString();
                    Map<String, Object> object = JSONObject.parseObject(str).toJavaObject(Map.class);
                    Counter counter = Counter.parseCounter(object);
                    counters.addCounter(counter);
                }
            }

        }

    }


    enum SingletonEnum {
        /**
         * 枚举实现的单例
         */
        INSTANCE;
        private final ConfigParser parser;

        SingletonEnum() {
            parser = new ConfigParser();
        }

        public ConfigParser getInstance() {
            return parser;
        }
    }
}


