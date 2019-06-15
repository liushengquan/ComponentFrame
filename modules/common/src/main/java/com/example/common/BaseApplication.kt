package com.example.common

import android.app.Application
import android.content.Context

open class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        mContext = this
    }

    companion object {
        lateinit var mContext: Context
        fun getAppContext() = (if (mContext != null) mContext else BaseApplication().applicationContext)!!
    }
}