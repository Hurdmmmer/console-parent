package com.tdh.gps.console.web.context;

/**
 * 一个 threadLocal 的包装类
 */
public class AuthenticationContext<T> {
    private ThreadLocal<T> map = new ThreadLocal<>();

    public T get() {
        return map.get();
    }

    public void add(T data) {
        this.map.set(data);
    }

    public void clean() {
        this.map.remove();
    }
}
