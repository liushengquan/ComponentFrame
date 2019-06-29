package com.example.main.activity

import android.os.Bundle
import com.example.common.activity.BaseActivity
import com.example.main.R
import com.tencent.bugly.crashreport.CrashReport
import kotlinx.android.synthetic.main.main_activity_main.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity_main)
        initView()
    }

    override fun initView() {
        btn_main_crashreport.setOnClickListener {
            CrashReport.testJavaCrash()
        }
    }

    override fun initData() {

    }
}