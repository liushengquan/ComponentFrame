package com.example.common.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import com.example.api.appinit.AppInitEvent
import com.example.common.base.BaseApp
import com.tencent.mars.xlog.Log
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Created by liushengquan on 2017/12/31.
 */
abstract class BaseActivity : AppCompatActivity(), BaseInit {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getContentView())
        BaseApp.addActivityToStack(this)
        initView()
        initData()
    }

    override fun onDestroy() {
        super.onDestroy()
        BaseApp.removeActivityFromStack(this)
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun appInitEvent(event: AppInitEvent.OnAppinitEvent) {
        Log.i("BaseActivity", "appInitEvent")
        appInitCallback()
    }

    abstract fun appInitCallback()

    fun addFragment(fragment: Fragment, frameId: Int) {
        supportFragmentManager.inTransaction { add(frameId, fragment) }
    }

    fun replaceFragment(fragment: Fragment, frameId: Int) {
        supportFragmentManager.inTransaction { replace(frameId, fragment) }
    }

    fun removeFragment(fragment: Fragment) {
        supportFragmentManager.inTransaction { remove(fragment) }
    }

    fun showFragment(fragment: Fragment) {
        supportFragmentManager.inTransaction { show(fragment) }
    }

    fun hideFragment(fragment: Fragment) {
        supportFragmentManager.inTransaction { hide(fragment) }
    }

    private fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
        beginTransaction().func().commitAllowingStateLoss()
    }
}