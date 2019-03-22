package com.lxf.netty.log;

import java.util.logging.Logger;

public class Log {
    private static final Logger logger = Logger.getLogger("netty");
    public static boolean DEBUG = true;

    public static void info(String msg) {
        if (DEBUG)
            logger.info(msg);
    }

    public static void warn(String msg) {
        if (DEBUG)
            logger.warning(msg);
    }

    public static void error(String msg) {
        if (DEBUG)
            logger.severe(msg);
    }
}
