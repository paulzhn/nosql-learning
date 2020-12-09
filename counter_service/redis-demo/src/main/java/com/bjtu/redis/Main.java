package com.bjtu.redis;

import com.bjtu.redis.action.ActionService;
import com.bjtu.redis.config.ConfigParser;
import com.bjtu.redis.config.entity.Action;
import com.bjtu.redis.config.entity.Actions;
import com.bjtu.redis.config.entity.Counter;
import com.bjtu.redis.config.entity.Counters;
import com.bjtu.redis.view.Menu;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        new Menu().entry();
    }


}
