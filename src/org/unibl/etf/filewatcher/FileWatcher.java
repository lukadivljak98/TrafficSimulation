package org.unibl.etf.filewatcher;

import org.unibl.etf.logger.MyLogger;
import org.unibl.etf.vozila.VozilaFactory;

import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

import java.io.IOException;
import java.nio.file.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileWatcher extends Thread {

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
            Path dir = Paths.get("./src/org/unibl/etf/filewatcher");
            dir.register(watcher, ENTRY_MODIFY);

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
                    if (fileName.toString().trim().endsWith(".properties") && kind.equals(ENTRY_MODIFY)) {

                        new Thread(new VozilaFactory()).start();
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