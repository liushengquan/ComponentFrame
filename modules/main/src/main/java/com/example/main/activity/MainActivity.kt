package com.example.main.activity

import com.example.common.activity.BaseActivity
import com.example.main.R
import com.tencent.mars.xlog.Log
import kotlinx.android.synthetic.main.main_activity_main.*

class MainActivity : BaseActivity() {

    override fun getContentView(): Int {
        return R.layout.main_activity_main
    }

    override fun initView() {
        Log.i("MainActivity", "initView")
        btn_main_crashreport.setOnClickListener {
            Log.i("MainActivity", "btn_main_crashreport click")
        }
    }

    override fun initData() {

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("MainActivity", "onDestroy")
        Log.appenderClose()
    }

    override fun appInitCallback() {
    }
}