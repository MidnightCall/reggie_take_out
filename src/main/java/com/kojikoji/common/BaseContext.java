package com.kojikoji.common;

/**
 * @ClassName BaseContext
 * @Description
 * @Author kojikoji 1310402980@qq.com
 * @Date 2023/3/26 16:14
 * @Version
 */

public class BaseContext {
    public static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }

    public static Long getCurrentId(){
        return threadLocal.get();
    }
}
