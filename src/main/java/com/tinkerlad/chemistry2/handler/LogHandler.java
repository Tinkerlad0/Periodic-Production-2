package com.tinkerlad.chemistry2.handler;

/**
 * Created by brock_000 on 5/01/2015.
 */

import com.tinkerlad.chemistry2.Chemistry;
import net.minecraftforge.fml.common.FMLLog;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.Logger;

public class LogHandler {

    private static Logger logger;

    public static void setLogger(Logger log) {
        logger = log;
    }

    public static void log(Level logLevel, Object object) {
        FMLLog.log(Chemistry.MODID, logLevel, String.valueOf(object));

        logger.log(logLevel, object);
    }

    public static void all(Object object) {
        log(Level.ALL, object);
    }

    public static void debug(Object object) {
        log(Level.DEBUG, object);
    }

    public static void error(Object object) {
        log(Level.ERROR, object);
    }

    public static void fatal(Object object) {
        log(Level.FATAL, object);
    }

    public static void info(Object object) {
        log(Level.INFO, object);
    }

    public static void off(Object object) {
        log(Level.OFF, object);
    }

    public static void trace(Object object) {
        log(Level.TRACE, object);
    }

    public static void warn(Object object) {
        log(Level.WARN, object);
    }
}