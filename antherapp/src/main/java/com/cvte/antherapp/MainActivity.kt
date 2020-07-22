package com.cvte.antherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var mAppClient: AppClient = AppClient()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initBtnListener()
    }

    private fun initBtnListener() {
        btn_start_server.setOnClickListener { mAppClient.startService(this) }
        btn_stop_server.setOnClickListener { mAppClient.stopService(this) }
        btn_bind_server.setOnClickListener { mAppClient.bindService(this) }
        btn_unbind_server.setOnClickListener { mAppClient.unbindService(this) }
        btn_sync.setOnClickListener { mAppClient.setDataToService(et_input.text.toString()) }
        btn_add_listener.setOnClickListener { mAppClient.addCallback() }
    }
}
