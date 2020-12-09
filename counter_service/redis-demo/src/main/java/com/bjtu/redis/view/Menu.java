package com.bjtu.redis.view;

import com.bjtu.redis.action.ActionService;
import com.bjtu.redis.config.ConfigParser;
import com.bjtu.redis.config.entity.*;

import java.util.*;

/**
 * @author bytedance
 */
public class Menu {
    public void entry() {
        ConfigParser.getInstance();
        while (true) {

            Scanner scanner = new Scanner(System.in);

            while (true) {
                int count = 1;
                System.out.println("Welcome to Paul's Counter Service");
                System.out.println("1. Show actions");
                System.out.println("2. Show counters");
                System.out.println("3. Execute action");
                System.out.println("Please input the choice: ");
                int choice = 0;
                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();
                } else {
                    scanner.next();
                }

                if (choice < 1 || choice > 3) {
                    System.err.println("Retry.");
                } else {
                    logic(choice);
                    break;
                }


            }
        }

    }

    private void logic(int choice) {
        switch (choice) {
            case 1:
                Actions actions = ConfigParser.getInstance().getActions();
                for (Map.Entry<String, Action> stringActionEntry : actions.getActionMap().entrySet()) {
                    System.out.println(stringActionEntry.getKey());
                }
                break;
            case 2:
                Counters counters = ConfigParser.getInstance().getCounters();
                for (Map.Entry<String, Counter> stringCounterEntry : counters.getCounterMap().entrySet()) {
                    System.out.println(stringCounterEntry.getKey());
                }
                break;
            case 3:
                try {


                    System.out.println("Please input the action: ");
                    Scanner scanner = new Scanner(System.in);
                    String actionName = scanner.nextLine();
                    Action action = getAction(actionName);
                    Map<String, List<Counter>> counter = getCounters(action);
                    Map<String, Object> values = new HashMap<>();
                    Set<Counter> counterSet = new HashSet<>();
                    List<Counter> save = counter.get("save");
                    counterSet.addAll(save);
                    List<Counter> retrieve = counter.get("retrieve");
                    counterSet.addAll(retrieve);
                    for (Counter counter1 : counterSet) {
                        getField(counter1, values);
                    }
                    ActionService service = new ActionService(actionName, values);
                    Map<String, Long> result = service.retrieve();
                    System.out.println(result);
                    service.save();
                } catch (Exception ex) {
                    System.err.println("Error occurs, err=" + ex.toString());
                }
                break;
            default:
                break;
        }
    }

    private Action getAction(String actionName) {
        return ConfigParser.getInstance().getActions().getAction(actionName);
    }

    private Map<String, List<Counter>> getCounters(Action action) {
        Map<String, List<Counter>> result = new HashMap<>();
        List<Counter> save = new ArrayList<>();
        for (String s : action.getSaveCounter()) {
            Counter counter = ConfigParser.getInstance().getCounters().getCounter(s);
            save.add(counter);
        }
        result.put("save", save);
        List<Counter> retrieve = new ArrayList<>();
        for (Map.Entry<String, String> entry : action.getRetrieveCounter().entrySet()) {
            Counter counter = ConfigParser.getInstance().getCounters().getCounter(entry.getKey());
            retrieve.add(counter);
        }
        result.put("retrieve", retrieve);
        return result;
    }


    private Map<String, Object> getField(Counter counter, Map<String, Object> values) {
        Scanner scanner = new Scanner(System.in);
        if (values == null) {
            values = new HashMap<>();
        }

        // 以下是很丑的遍历

        List<String> keys = counter.getKeyFields();
        if (keys != null) {
            for (String s : keys) {
                routine(scanner, values, s);
            }
        }

        keys = counter.getValueFields();
        if (keys != null) {
            for (String s : keys) {
                routine(scanner, values, s);
            }
        }

        return values;
    }

    private void routine(Scanner scanner, Map<String, Object> values, String key) {
        System.out.println("Please input `" + key + "`:");
        String value = scanner.nextLine();
        values.put(key, value);
    }
}
