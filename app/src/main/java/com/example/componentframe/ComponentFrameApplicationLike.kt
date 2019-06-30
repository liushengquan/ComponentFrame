package com.example.componentframe

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Environment
import android.support.multidex.MultiDex
import com.example.common.util.ProcessUtil
import com.tencent.bugly.Bugly
import com.tencent.bugly.beta.Beta
import com.tencent.bugly.crashreport.CrashReport
import com.tencent.tinker.entry.DefaultApplicationLike
import com.umeng.analytics.MobclickAgent
import com.umeng.commonsdk.UMConfigure

class ComponentFrameApplicationLike(application: Application, tinkerFlags: Int, tinkerLoadVerifyFlag: Boolean, applicationStartElapsedTime: Long, applicationStartMillisTime: Long, tinkerResultIntent: Intent) : DefaultApplicationLike(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent) {

    override fun onCreate() {
        super.onCreate()
        initBugly()
        initUmeng()
    }

    private fun initBugly() {
        val packageName = application.packageName
        // 获取当前进程名
        val processName = ProcessUtil.getProcessName(android.os.Process.myPid())
        // 设置是否为上报进程
        val strategy = CrashReport.UserStrategy(application)
        strategy.isUploadProcess = processName == null || processName == packageName
        //设置为开发设备
//        CrashReport.setIsDevelopmentDevice(context, BuildConfig.DEBUG)
        // 初始化Bugly
//        CrashReport.initCrashReport(context,Config.BUGLY_APP_ID, true, strategy)
        Beta.autoInit = true
        Beta.autoCheckUpgrade = true
        Beta.storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        Beta.enableNotification = true
        Beta.canShowApkInfo = true
//        Bugly.setIsDevelopmentDevice(application, true)
        Bugly.init(application, Config.BUGLY_APP_ID, true)
    }

    private fun initUmeng(){
        UMConfigure.init(application, Config.UMENG_KEY, Config.UMENG_CHANEL, UMConfigure.DEVICE_TYPE_PHONE, Config.UMENG_PUSH_SECRET)
        //当用户使用自有账号登录时，可以这样统计：
        MobclickAgent.onProfileSignIn("101")
        // 选用AUTO页面采集模式
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO)
        // 支持在子进程中统计自定义事件
        UMConfigure.setProcessEvent(true)
        // 打开统计SDK调试模式
        UMConfigure.setLogEnabled(true)
    }

    override fun onBaseContextAttached(base: Context?) {
        super.onBaseContextAttached(base)
        MultiDex.install(base)
        // 安装tinker
        Beta.installTinker(this)
    }
}