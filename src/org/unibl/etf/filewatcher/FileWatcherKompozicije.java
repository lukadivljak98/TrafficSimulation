package org.unibl.etf.filewatcher;

import org.unibl.etf.kompozicije.KompozicijaFactory;
import org.unibl.etf.logger.MyLogger;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;

import java.io.IOException;
import java.nio.file.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileWatcherKompozicije extends Thread {

    static {
        try {
            MyLogger.setup();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            WatchService watcher = FileSystems.getDefault().newWatchService();
            Path dir = Paths.get("./src/org/unibl/etf/kompozicije/vozovi/");
            dir.register(watcher, ENTRY_CREATE);

            while (true) {
                WatchKey key;
                try {
                    key = watcher.take();
                } catch (InterruptedException ex) {
                    return;
                }

                try {
                    sleep(100);
                } catch (Exception e) {
                    Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.WARNING, e.fillInStackTrace().toString());
                }

                for (WatchEvent<?> event : key.pollEvents()) {
                    WatchEvent.Kind<?> kind = event.kind();
                    WatchEvent<Path> ev = (WatchEvent<Path>) event;
                    Path fileName = ev.context();
                    if (fileName.toString().trim().endsWith(".txt") && kind.equals(ENTRY_CREATE)) {
                        new Thread(new KompozicijaFactory()).start();
                    }
                }

                boolean valid = key.reset();
                if (!valid) {
                    break;
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.WARNING, ex.fillInStackTrace().toString());
        }
    }
}