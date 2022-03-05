package org.unibl.etf.logger;

import java.io.IOException;
import java.util.logging.*;

public class MyLogger {

    static private FileHandler logFile;

    public static void setup() throws IOException {
        Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
        logger.setLevel(Level.INFO);
        logFile = new FileHandler("./src/org/unibl/etf/logger/Logger.log");
        logger.addHandler(logFile);
    }
}