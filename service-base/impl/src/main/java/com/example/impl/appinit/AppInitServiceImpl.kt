package com.example.impl.appinit

import android.content.Context
import android.os.Environment
import com.example.api.ApiConfig
import com.example.api.appinit.IAppInitService
import com.tencent.mars.xlog.Log
import com.tencent.mars.xlog.Xlog

class AppInitServiceImpl private constructor(val context: Context) : IAppInitService {

    init {
        initXlog()
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


    override fun onServiceStart() {

    }

    override fun onServiceStop() {
    }
}