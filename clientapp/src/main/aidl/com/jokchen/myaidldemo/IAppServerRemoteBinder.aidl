// IAppServerRemoteBinder.aidl
package com.jokchen.myaidldemo;
import com.jokchen.myaidldemo.IAntherAppListener;

// Declare any non-default types here with import statements

interface IAppServerRemoteBinder {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);
    void registerListener(IAntherAppListener listener);
    void unregisterListener(IAntherAppListener listener);
    void setData(String data);
}
