package com.tinkerlad.chemistry2.handler;

/**
 * Created by brock_000 on 5/01/2015.
 */

import com.tinkerlad.chemistry2.Chemistry;
import net.minecraftforge.fml.common.FMLLog;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.Logger;

public class LogHandler {

    private static LogHandler instance = new LogHandler();

    private Logger logger;

    public static LogHandler getInstance() {
        return instance;
    }

    public void setLogger(Logger log) {
        logger = log;
    }

    public void log(Level logLevel, Object object) {
        FMLLog.log(Chemistry.MODID, logLevel, String.valueOf(object));

        logger.log(logLevel, object);
    }

    public void all(Object object) {
        log(Level.ALL, object);
    }

    public void debug(Object object) {
        log(Level.DEBUG, object);
    }

    public void error(Object object) {
        log(Level.ERROR, object);
    }

    public void fatal(Object object) {
        log(Level.FATAL, object);
    }

    public void info(Object object) {
        log(Level.INFO, object);
    }

    public void off(Object object) {
        log(Level.OFF, object);
    }

    public void trace(Object object) {
        log(Level.TRACE, object);
    }

    public void warn(Object object) {
        log(Level.WARN, object);
    }
}