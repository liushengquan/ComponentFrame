package com.example.componentframe

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Environment
import android.support.multidex.MultiDex
import com.example.api.report.IReportService
import com.example.common.BaseApp
import com.example.common.router.Router
import com.example.common.util.ProcessUtil
import com.example.impl.report.ReportServiceImpl
import com.tencent.bugly.Bugly
import com.tencent.bugly.beta.Beta
import com.tencent.bugly.crashreport.CrashReport
import com.tencent.tinker.entry.DefaultApplicationLike

class ComponentFrameApplicationLike(application: Application, tinkerFlags: Int, tinkerLoadVerifyFlag: Boolean, applicationStartElapsedTime: Long, applicationStartMillisTime: Long, tinkerResultIntent: Intent) : DefaultApplicationLike(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent) {

    override fun onCreate() {
        super.onCreate()
        BaseApp.setAppContext(application)
        initBugly()
        initAllService()
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
//        CrashReport.initCrashReport(context,ApiConfig.BUGLY_APP_ID, true, strategy)
        Beta.autoInit = true
        Beta.autoCheckUpgrade = true
        Beta.storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        Beta.enableNotification = true
        Beta.canShowApkInfo = true
//        Bugly.setIsDevelopmentDevice(application, true)
        Bugly.init(application, Config.BUGLY_APP_ID, true)
    }

    private fun initAllService(){
        val router = Router.getInstance()
        router.registerService(IReportService::class.simpleName!!, ReportServiceImpl.init(BaseApp.getAppContext()))
    }

    override fun onBaseContextAttached(base: Context?) {
        super.onBaseContextAttached(base)
        MultiDex.install(base)
        // 安装tinker
        Beta.installTinker(this)
    }
}