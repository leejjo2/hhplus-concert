package com.hhplusconcert.concert.aop;

public class QueueTokenHolder {
    private static final ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static String getToken() {
        return threadLocal.get();
    }

    public static void setToken(String token) {
        threadLocal.set(token);
    }

    public static void clearToken() {
        threadLocal.remove();
    }

}
