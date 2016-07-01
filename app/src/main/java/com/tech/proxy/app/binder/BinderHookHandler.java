package com.tech.proxy.app.binder;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author keith
 * @since 2016-07-01
 */
public class BinderHookHandler implements InvocationHandler {

    private static final String TAG = "BinderHookHandler";

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return null;
    }

}
