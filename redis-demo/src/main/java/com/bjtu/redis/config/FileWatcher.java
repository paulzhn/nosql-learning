package com.bjtu.redis.config;

import com.bjtu.redis.Const;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import java.io.File;
import java.net.URL;

/**
 * @author paul
 */
class FileWatcher {
    /**
     * in ms
     */
    private static final int INTERVAL = 1000;

    void start() {
        URL file = getClass().getResource("/counters.json");
        String path = file.getPath();
        String parent = path.substring(0, path.indexOf("counters.json"));

        FileAlterationObserver fileObserver = new FileAlterationObserver(parent);

        fileObserver.addListener(new FileAlterationListenerAdaptor() {
            @Override
            public void onFileChange(File file) {
                super.onFileChange(file);
                if ("counters.json".equals(file.getName())) {
                    System.out.println("actions.json has changed, loading...");
                    ConfigParser.getInstance().loadConfig(Const.ACTION_WATCHER);
                } else if ("actions.json".equals(file.getName())) {
                    System.out.println("counters.json has changed, loading...");
                    ConfigParser.getInstance().loadConfig(Const.COUNTER_WATCHER);
                }


            }
        });
        FileAlterationMonitor monitor = new FileAlterationMonitor(INTERVAL);
        monitor.addObserver(fileObserver);


        try {
            monitor.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

