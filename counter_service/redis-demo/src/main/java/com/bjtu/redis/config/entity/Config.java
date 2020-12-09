package com.bjtu.redis.config.entity;

/**
 * @author paul
 */
public class Config {
    private Counters counters;
    private Actions actions;

    private Config() {
        counters = new Counters();
        actions = new Actions();
    }

    public static Config getInstance() {
        return Singleton.INSTANCE.getInstance();
    }

    public Counters getCounters() {
        return counters;
    }

    public void setCounters(Counters counters) {
        this.counters = counters;
    }

    public Actions getActions() {
        return actions;
    }

    public void setActions(Actions actions) {
        this.actions = actions;
    }

    enum Singleton {
        /**
         * 单例
         */
        INSTANCE;
        private final Config config = new Config();

        public Config getInstance() {
            return config;
        }
    }
}

