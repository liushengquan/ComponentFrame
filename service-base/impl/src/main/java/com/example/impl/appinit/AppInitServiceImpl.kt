package com.example.impl.appinit

import android.app.Application
import android.content.Context
import android.os.Environment
import com.alibaba.android.arouter.launcher.ARouter
import com.example.api.ApiConfig
import com.example.api.BuildConfig
import com.example.api.appinit.IAppInitService
import com.tencent.imsdk.TIMConversation
import com.tencent.imsdk.TIMGroupTipsElem
import com.tencent.imsdk.TIMMessage
import com.tencent.imsdk.TIMSdkConfig
import com.tencent.mars.xlog.Log
import com.tencent.mars.xlog.Xlog
import com.tencent.qcloud.tim.uikit.TUIKit
import com.tencent.qcloud.tim.uikit.base.IMEventListener
import com.tencent.qcloud.tim.uikit.config.CustomFaceConfig
import com.tencent.qcloud.tim.uikit.config.GeneralConfig


class AppInitServiceImpl private constructor(val context: Context) : IAppInitService {

    private val Tag = AppInitServiceImpl::class.simpleName

    init {
        initXlog()
        initTencentIm()
        initARouter()
    }

    companion object {

        @Volatile
        var mAppInitService: IAppInitService? = null

        fun init(context: Context): IAppInitService {
            if (mAppInitService == null) {
                synchronized(AppInitServiceImpl::class) {
                    if (mAppInitService == null) {
                        mAppInitService = AppInitServiceImpl(context)
                    }
                }
            }
            return mAppInitService!!
        }
    }

    private fun initXlog() {
        System.loadLibrary("c++_shared")
        System.loadLibrary("marsxlog")

        val sdcard = Environment.getExternalStorageDirectory().absolutePath
        val logPath = "$sdcard/${ApiConfig.XLOG_DIR}"

        // this is necessary, or may crash for SIGBUS
        val cachePath = context.filesDir.absolutePath + "/xlog"

        //init xlog
        Xlog.appenderOpen(Xlog.LEVEL_DEBUG, Xlog.AppednerModeAsync, cachePath, logPath, "Douban", 0, ApiConfig.XLOG_PUB_KEY)
        Xlog.setConsoleLogOpen(true)
//      Xlog.appenderOpen(Xlog.LEVEL_INFO, Xlog.AppednerModeSync, cachePath, logPath, "MarsSample", 0, "")
//      Xlog.setConsoleLogOpen(false)
        Log.setLogImp(Xlog())
    }

    fun initTencentIm() {
        val configs = TUIKit.getConfigs()
        configs.sdkConfig = TIMSdkConfig(ApiConfig.TENCENT_IM_APPID)
        configs.customFaceConfig = CustomFaceConfig()
        configs.generalConfig = GeneralConfig()

        TUIKit.setIMEventListener(object : IMEventListener() {
            override fun onNewMessages(msgs: MutableList<TIMMessage>?) {
                super.onNewMessages(msgs)
                Log.i(Tag, "onNewMessages : %s", msgs?.toString())
            }

            override fun onUserSigExpired() {
                super.onUserSigExpired()
            }

            override fun onConnected() {
                super.onConnected()
            }

            override fun onGroupTipsEvent(elem: TIMGroupTipsElem?) {
                super.onGroupTipsEvent(elem)
            }

            override fun onWifiNeedAuth(name: String?) {
                super.onWifiNeedAuth(name)
            }

            override fun onForceOffline() {
                super.onForceOffline()
                Log.i(Tag, "onForceOffline")
            }

            override fun onDisconnected(code: Int, desc: String?) {
                super.onDisconnected(code, desc)
            }

            override fun onRefreshConversation(conversations: MutableList<TIMConversation>?) {
                super.onRefreshConversation(conversations)
            }
        })

        TUIKit.init(context, ApiConfig.TENCENT_IM_APPID, configs)
    }

    private fun initARouter() {
        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(context as Application)
    }

    override fun onServiceStart() {

    }

    override fun onServiceStop() {
    }
}