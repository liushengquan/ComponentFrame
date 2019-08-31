package com.example.main.activity

import android.support.v4.app.FragmentManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.common.activity.BaseActivity
import com.example.common.fragment.BaseFragment
import com.example.common.router.constant.MainConstant
import com.example.main.R
import com.example.main.activity.address.MainAddressFragment
import com.example.main.activity.me.MainMeFragment
import com.example.main.activity.messege.MainMessegeFragment
import com.tencent.mars.xlog.Log
import kotlinx.android.synthetic.main.main_activity_main.*

@Route(path = MainConstant.Mactivity)
class MainActivity : BaseActivity() {
    private val TAG = MainActivity::class.simpleName

    private val mFragmentList = mutableListOf<BaseFragment>()
    private lateinit var mCurrFragment: BaseFragment
    private lateinit var mFragmentManager: FragmentManager

    override fun getContentView(): Int {
        return R.layout.main_activity_main
    }

    override fun initView() {
        mFragmentManager = supportFragmentManager
        ll_main_message.setOnClickListener {
            Log.i(TAG, "ll_main_message click")
            if (mCurrFragment !is MainMessegeFragment) {
                showOrHideFragment(mCurrFragment, mFragmentList[0])
            }
        }

        ll_main_users.setOnClickListener {
            Log.i(TAG, "ll_main_users click")
            if (mCurrFragment !is MainAddressFragment) {
                showOrHideFragment(mCurrFragment, mFragmentList[1])
            }
        }

        ll_main_me.setOnClickListener {
            Log.i(TAG, "ll_main_me click")
            if (mCurrFragment !is MainMeFragment) {
                showOrHideFragment(mCurrFragment, mFragmentList[2])
            }
        }
    }

    private fun showOrHideFragment(hideFragment: BaseFragment, showFragment: BaseFragment) {
        val transaction = mFragmentManager.beginTransaction()
        transaction.hide(hideFragment)
        transaction.show(showFragment)
        transaction.commitNowAllowingStateLoss()
        mCurrFragment = showFragment
    }

    override fun initData() {
        val transaction = mFragmentManager.beginTransaction()
        mFragmentList.add(MainMessegeFragment.newInstance())
        mFragmentList.add(MainAddressFragment.newInstance())
        mFragmentList.add(MainMeFragment.newInstance())
        transaction.add(R.id.rl_main_container, mFragmentList[0])
        transaction.add(R.id.rl_main_container, mFragmentList[1])
        transaction.add(R.id.rl_main_container, mFragmentList[2])
        transaction.hide(mFragmentList[1])
        transaction.hide(mFragmentList[2])
        transaction.commitNowAllowingStateLoss()
        mCurrFragment = mFragmentList[0]
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy")
        Log.appenderClose()
    }

    override fun appInitCallback() {
    }
}