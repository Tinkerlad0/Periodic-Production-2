package com.tinkerlad.chemistry2.handler;

/**
 * Created by brock_000 on 5/01/2015.
 */

import com.tinkerlad.chemistry2.Chemistry;
import org.apache.logging.log4j.Level;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogHandler {

    private final static DateFormat df = new SimpleDateFormat("yyyy.MM.dd  hh:mm:ss ");
    private static String logFile = Chemistry.mcDir + "\\logs\\tnkchem2.log";

    public static void init() {
        try {
            File log = new File(logFile);
            log.delete();
            log.createNewFile();
            Date now = new Date();
            String currentTime = df.format(now);
            FileWriter aWriter = new FileWriter(log, true);
            aWriter.write("Beginning Periodic Production 2 Log File: -->" + System.lineSeparator());
            aWriter.flush();
            aWriter.close();
        } catch (Exception e) {
            System.out.println(stack2string(e));
        }
    }

    public static void all(Object object) {
        write(Level.ALL, object.toString());
    }

    public static void debug(Object object) {
        write(Level.DEBUG, object.toString());
    }

    public static void error(Object object) {
        write(Level.ERROR, object.toString());
    }

    public static void fatal(Object object) {
        write(Level.FATAL, object.toString());
    }

    public static void info(Object object) {
        write(Level.INFO, object.toString());
    }

    public static void off(Object object) {
        write(Level.OFF, object.toString());
    }

    public static void trace(Object object) {
        write(Level.TRACE, object.toString());
    }

    public static void warn(Object object) {
        write(Level.WARN, object.toString());
    }

    public static void write(Level level, String msg) {
        write(logFile, level, msg);
    }

    public static void write(String msg) {
        write(logFile, Level.INFO, msg);
    }

    public static void write(Exception e) {
        write(logFile, Level.ERROR, stack2string(e));
    }

    public static void write(String file, Level level, String msg) {
        try {
            File log = new File(file);
            log.createNewFile();
            Date now = new Date();
            String currentTime = df.format(now);
            FileWriter aWriter = new FileWriter(log, true);
            aWriter.write("[" + currentTime + "] [" + level.name() + "] " + msg + System.lineSeparator());
            aWriter.flush();
            aWriter.close();
        } catch (Exception e) {
            System.out.println("file = " + file);
            System.out.println(stack2string(e));
        }
    }

    private static String stack2string(Exception e) {
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            return "------\r\n" + sw.toString() + "------\r\n";
        } catch (Exception e2) {
            return "bad stack2string";
        }
    }
}