package com.projectTakeAway.Anima.common;
@SuppressWarnings("all")

/**
 * @author CodemyMind
 * @Date 2022/5/16 10:06 PM
 * Description：             基于ThreadLocal封装工具类，用户保存和获取当前登陆用户的id
 */
public class BaseContext {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    // 设置当前线程的线程局部变量的值
    public static void setCurrentId(Long id) {
        threadLocal.set(id);
    }

    // 返回当前线程所对应的线程局部变量的值
    public static Long getCurrentId() {
        return threadLocal.get();
    }
}
