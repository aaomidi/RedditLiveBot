package me.stuntguy3000.java.redditlivebot.handler;

/**
 * Created by amir on 2015-11-25. Modified by stuntguy3000.
 */
public class LogHandler {
    public static void log(String s, Object... format) {
        System.out.println(String.format(s, format));
    }
}
