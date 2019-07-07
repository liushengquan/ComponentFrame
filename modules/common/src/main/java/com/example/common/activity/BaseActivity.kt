package com.example.common.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import com.example.common.base.BaseApp

/**
 * Created by liushengquan on 2017/12/31.
 */
abstract class BaseActivity : AppCompatActivity(),BaseInit {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BaseApp.addActivityToStack(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        BaseApp.removeActivityFromStack(this)
    }

    fun addFragment(fragment: Fragment, frameId: Int){
        supportFragmentManager.inTransaction { add(frameId, fragment) }
    }

    fun replaceFragment(fragment: Fragment, frameId: Int) {
        supportFragmentManager.inTransaction{replace(frameId, fragment)}
    }

    fun removeFragment(fragment: Fragment) {
        supportFragmentManager.inTransaction{remove(fragment)}
    }

    fun showFragment(fragment: Fragment) {
        supportFragmentManager.inTransaction{show(fragment)}
    }

    fun hideFragment(fragment: Fragment) {
        supportFragmentManager.inTransaction{hide(fragment)}
    }

    private fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
        beginTransaction().func().commitAllowingStateLoss()
    }
}