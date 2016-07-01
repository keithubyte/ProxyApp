package com.tech.proxy.app;

import android.app.Application;
import android.app.Instrumentation;
import android.content.Context;

import com.tech.proxy.app.instrumentation.ProxyInstrumentation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author keith
 * @since 2016-07-01
 */
public class ProxyApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        try {
            attachProxyInstrumentation();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void attachProxyInstrumentation() throws Exception {
        // 先获取到当前的 ActivityThread 对象
        Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
        Method currentActivityThreadMethod = activityThreadClass.getDeclaredMethod("currentActivityThread");
        currentActivityThreadMethod.setAccessible(true);
        Object currentActivityThread = currentActivityThreadMethod.invoke(null);

        // 拿到原始的 mInstrumentation 字段
        Field mInstrumentationField = activityThreadClass.getDeclaredField("mInstrumentation");
        mInstrumentationField.setAccessible(true);
        Instrumentation mInstrumentation = (Instrumentation) mInstrumentationField.get(currentActivityThread);

        // 创建代理对象
        Instrumentation proxyInstrumentation = new ProxyInstrumentation(mInstrumentation);

        // 偷梁换柱
        mInstrumentationField.set(currentActivityThread, proxyInstrumentation);
    }
}
