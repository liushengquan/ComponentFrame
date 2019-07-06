package com.example.impl.report

import android.content.Context
import com.example.api.ApiConfig
import com.example.api.report.IReportService
import com.umeng.analytics.MobclickAgent
import com.umeng.commonsdk.UMConfigure

class ReportServiceImpl private constructor(private val context: Context) : IReportService {

    init {
        initUmeng()
    }

    private fun initUmeng() {
        // 支持在子进程中统计自定义事件
        UMConfigure.setProcessEvent(true)
        // 打开统计SDK调试模式
        UMConfigure.setLogEnabled(true)
        UMConfigure.init(context, ApiConfig.UMENG_KEY, ApiConfig.UMENG_CHANEL, UMConfigure.DEVICE_TYPE_PHONE, ApiConfig.UMENG_PUSH_SECRET)
        // 选用AUTO页面采集模式
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO)
        //打开错误统计功能
        MobclickAgent.setCatchUncaughtExceptions(true)
    }

    companion object {
        private lateinit var mReportService: ReportServiceImpl

        fun init(context: Context): IReportService {
            mReportService = ReportServiceImpl(context)
            return mReportService
        }
    }

    override fun onServiceStart() {
    }

    override fun onServiceStop() {
    }

    override fun umengOnProfileSignIn(userId: String) {
        //当用户使用自有账号登录时，可以这样统计：
        MobclickAgent.onProfileSignIn(userId)
    }

    override fun umengOnProfileSignOff() {
        MobclickAgent.onProfileSignOff()
    }

    override fun umengOnPageStart(viewName: String) {
        MobclickAgent.onPageStart(viewName)
    }

    override fun umengOnPageEnd(viewName: String) {
        MobclickAgent.onPageEnd(viewName)
    }

    override fun umengOnEvent(context: Context, eventID: String) {
        MobclickAgent.onEvent(context, eventID)
    }

    override fun umengOnEvent(context: Context, eventID: String, label: String) {
        MobclickAgent.onEvent(context, eventID, label)
    }

    override fun umengOnEvent(context: Context, eventID: String, map: Map<String, String>) {
        MobclickAgent.onEvent(context, eventID, map)
    }

    override fun umengOnEventValue(context: Context, eventID: String, map: Map<String, String>, du: Int) {
        MobclickAgent.onEventValue(context, eventID, map, du)
    }

    override fun umengSetFirstLaunchEvent(context: Context, list: List<String>) {
        MobclickAgent.setFirstLaunchEvent(context, list)
    }

    override fun umengReportError(context: Context, e: Throwable) {
        MobclickAgent.reportError(context, e)
    }

    override fun umengReportError(context: Context, error: String) {
        MobclickAgent.reportError(context, error)
    }
}