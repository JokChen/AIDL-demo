package com.jokchen.myaidldemo

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.RemoteCallbackList
import android.util.Log
import com.jokchen.myaidldemo.IAntherAppListener
import com.jokchen.myaidldemo.IAppServerRemoteBinder

class AppServer : Service() {
    private val TAG: String = "AppServer"
    var mData: String = "data"
    var mRunning: Boolean = false

    // 这里必须要用RemoteCallbackList这种list类型
    var mListeners = RemoteCallbackList<IAntherAppListener>()

    // 匿名内部类，用于当客户端连接的时候返回的值
    private val mAppServerRemoteBinder: IAppServerRemoteBinder =
        object : IAppServerRemoteBinder.Stub() {
            override fun basicTypes(
                anInt: Int,
                aLong: Long,
                aBoolean: Boolean,
                aFloat: Float,
                aDouble: Double,
                aString: String?
            ) {
                TODO("Not yet implemented")
            }

            override fun registerListener(listener: IAntherAppListener?) {
                mListeners.register(listener)
            }

            override fun unregisterListener(listener: IAntherAppListener?) {
                mListeners.unregister(listener)
            }

            override fun setData(data: String?) {
                if (data != null) {
                    mData = data
                }
            }
        }

    override fun onBind(intent: Intent): IBinder? {
        return mAppServerRemoteBinder.asBinder()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "server start")
        printDataAndDoCallback()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "server destroy")
        mRunning = false
    }

    /**
     * 用于打印mData数据如果有客户端注册了则回调回调客户端，打印mData是用于测试客户端调用setData接口的结果
     */
    private fun printDataAndDoCallback() {
        Handler().post(Runnable {
            mRunning = true
            while (mRunning) {
                Log.i(TAG, "#####################1############################")
                Log.i(TAG, mData)
                var counts: Int = mListeners.beginBroadcast()
                for (i in 0 until counts) {
                    mListeners.getBroadcastItem(i).callApp()
                }
                mListeners.finishBroadcast();
                Log.i(TAG, "#####################2############################")
                Thread.sleep(3000)
            }
        })
    }
}

