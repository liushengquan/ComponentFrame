package com.example.common.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Gravity
import android.widget.PopupWindow
import android.widget.Toast
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.common.BaseApplication
import com.example.common.R
import com.example.common.activity.BaseInit

/**
 * Created by liushengquan on 2017/12/31.
 */
abstract class BaseFragment : Fragment(), BaseInit {

    lateinit var mContext: Context
    var mPopLoading: PopupWindow? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = BaseApplication.getAppContext()
        //初始化loading
        val view = LayoutInflater.from(mContext).inflate(R.layout.common_popup_loading, null, false)
        mPopLoading = PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true)
    }

    /**
     * 根据传入的类(class)打开指定的activity
     * @param pClass
     */
    protected fun startActivity(pClass: Class<*>) {
        val intent = Intent()
        intent.setClass(activity, pClass)
        startActivity(intent)
    }

    protected fun startActivityByIntent(pIntent: Intent) {
        startActivity(pIntent)
    }

    protected fun showToast(resId: Int) {
        showToast(getString(resId))
    }

    protected fun showToast(message: String) {
        Toast.makeText(mContext, message, Toast.LENGTH_LONG).show()
    }

    protected fun showProgress(resId: Int) {
        var title = mContext.resources.getString(resId)
        showProgress(title)
    }

    protected fun showProgress(title: String) {
        mPopLoading?.run {
            if (isShowing)
                showAtLocation(view, Gravity.CENTER, 0, 0)
        }
    }

    protected fun hideProgress() {
        mPopLoading?.run {
            if (isShowing)
                dismiss()
        }
    }
}