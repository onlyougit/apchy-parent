package com.sptwin.apchy.order.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class DynamicInvocationHandler<T> implements InvocationHandler {
    private T t;

    public DynamicInvocationHandler(T t){
        this.t = t;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("调用前业务处理。。。。。。。。。。");
        Object result = method.invoke(t, args);
        System.out.println("调用后业务处理。。。。。。。。。。");
        return result;
    }
}
