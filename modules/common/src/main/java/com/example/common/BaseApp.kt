package com.example.common

import android.app.Activity
import android.app.Application
import android.content.Context
import java.lang.ref.WeakReference
import java.util.*

open class BaseApp : Application() {

    override fun onCreate() {
        super.onCreate()
        mRefContext = WeakReference(this)
    }

    companion object {
        lateinit var mRefContext: WeakReference<Context>
        fun getAppContext(): Context = mRefContext?.get()!!
        fun setAppContext(context: Context) {
            mRefContext = WeakReference(context)
        }

        val mActivityStack: Stack<Activity> = Stack()

        fun addActivityToStack(activity: Activity){
            mActivityStack.add(activity)
        }

        fun removeActivityFromStack(activity: Activity){
            mActivityStack.remove(activity)
        }

        fun getTopActivity(): Activity? = if(mActivityStack?.size>0) mActivityStack.peek() else null

        fun exitApp(){
            while (!mActivityStack.isEmpty()){
                val activity = mActivityStack.pop()
                activity.finish()
            }
            mRefContext.clear()
        }
    }
}