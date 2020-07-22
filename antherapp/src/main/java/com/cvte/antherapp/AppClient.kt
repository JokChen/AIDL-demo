package com.cvte.antherapp

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import com.cvte.myaidldemo.IAntherAppListener
import com.cvte.myaidldemo.IAppServerRemoteBinder

/**
 * @brief
 * @author  chenxiuning
 * @since 2020/7/22 11:51
 */
class AppClient() {
    private val TAG: String = "AppClient"
    private val appIntent: Intent = Intent()
    // 用于保存绑定成功后的app的IAppServerRemoteBinder继承对象，方便client调用service
    private lateinit var mBinder: IAppServerRemoteBinder
    private var isBind: Boolean = false
    init {
        appIntent.component = ComponentName(
            "com.cvte.myaidldemo",
            "com.cvte.myaidldemo.AppServer"
        )
    }

    // 用于绑定或者解除绑定app
    private val mServiceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            Log.i(TAG, "unbind app")
            isBind = false
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Log.i(TAG, "bind app")
            // 必须要用asInterface这个方法转换一下
            mBinder = IAppServerRemoteBinder.Stub.asInterface(service)
            isBind = true
        }
    }

    fun startService(context: Context) {
        context.startService(appIntent)
    }

    fun stopService(context: Context) {
        context.stopService(appIntent)
    }

    fun bindService(context: Context){
        context.bindService(appIntent, mServiceConnection, Context.BIND_AUTO_CREATE)
    }

    fun unbindService(context: Context){
        context.unbindService(mServiceConnection)
    }

    fun setDataToService(data:String){
        if (isBind) {
            mBinder!!.setData(data)
        } else {
            Log.e(TAG, "service don't bind")
        }
    }

    fun addCallback(){
        if (isBind) {
            mBinder!!.registerListener(object : IAntherAppListener.Stub() {
                override fun callApp() {
                    Log.i(TAG, "call app back" + System.nanoTime())
                }
            })
        } else {
            Log.e(TAG, "service don't bind")
        }
    }
}