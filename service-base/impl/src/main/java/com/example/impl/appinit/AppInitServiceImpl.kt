package com.example.impl.appinit

import android.content.Context
import android.os.Environment
import com.example.api.ApiConfig
import com.example.api.appinit.IAppInitService
import com.tencent.mars.xlog.Log
import com.tencent.mars.xlog.Xlog
import java.lang.ref.WeakReference

class AppInitServiceImpl private constructor(val context: Context) : IAppInitService {

    init {
        initXlog()
    }

    companion object {
        private lateinit var mRefer: WeakReference<IAppInitService>

        fun init(context: Context): IAppInitService {
            if (mRefer == null) {
                mRefer = WeakReference(AppInitServiceImpl(context))
            }
            return mRefer.get()!!
        }
    }

    fun initXlog() {
        System.loadLibrary("c++_shared")
        System.loadLibrary("marsxlog")

        val SDCARD = Environment.getExternalStorageDirectory().absolutePath
        val logPath = "$SDCARD/${ApiConfig.XLOG_DIR}"

        // this is necessary, or may crash for SIGBUS
        val cachePath = context.filesDir.absolutePath + "/xlog"

        //init xlog
        Xlog.appenderOpen(Xlog.LEVEL_DEBUG, Xlog.AppednerModeAsync, cachePath, logPath, "MarsSample", 0, "")
        Xlog.setConsoleLogOpen(true)
//            Xlog.appenderOpen(Xlog.LEVEL_INFO, Xlog.AppednerModeSync, cachePath, logPath, "MarsSample", 0, "")
//            Xlog.setConsoleLogOpen(false)
        Log.setLogImp(Xlog())
    }


    override fun onServiceStart() {

    }

    override fun onServiceStop() {
    }
}