package com.example.common.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.Toast
import com.example.api.report.IReportService
import com.example.common.BaseApp
import com.example.common.R
import com.example.common.activity.BaseActivity
import com.example.common.activity.BaseInit
import com.example.common.router.Router


/**
 * Created by liushengquan on 2017/12/31.
 */
abstract class BaseFragment : Fragment(), BaseInit {

    private lateinit var mContext: Context
    private lateinit var mPopLoading: PopupWindow
    private lateinit var mActivity: BaseActivity

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        this.mActivity = context as BaseActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = BaseApp.getAppContext()
        //初始化loading
        val view = LayoutInflater.from(mContext).inflate(R.layout.common_popup_loading, null, false)
        mPopLoading = PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true)
    }

    /**
     * 获取宿主Activity
     * @return BaseActivity
     */
    fun getHoldingActivity(): BaseActivity {
        return mActivity
    }

    override fun onResume() {
        super.onResume()
        (Router.getInstance().getService(IReportService::class.simpleName!!) as IReportService).umengOnPageStart(this::class.simpleName!!)
    }

    override fun onPause() {
        super.onPause()
        (Router.getInstance().getService(IReportService::class.simpleName!!) as IReportService).umengOnPageEnd(this::class.simpleName!!)
    }

    /**
     * 根据传入的类(class)打开指定的activity
     * @param pClass
     */
    protected fun startActivity(pClass: Class<*>, bundle: Bundle?) {
        val intent = Intent()
        intent.setClass(mContext, pClass)
        bundle?.apply {
            intent.putExtras(bundle)
        }
        startActivity(intent)
    }

    protected fun startActivityForResult(requestCode: Int, pClass: Class<*>, bundle: Bundle?) {
        val intent = Intent()
        intent.setClass(mContext, pClass)
        bundle?.apply {
            intent.putExtras(bundle)
        }
        startActivityForResult(intent, requestCode)
    }

    protected fun showToast(resId: Int) {
        showToast(mContext.resources.getString(resId))
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

    /**
     * 添加fragment
     *
     * @param fragment
     * @param frameId
     */
    protected fun addFragment(fragment: BaseFragment, @IdRes frameId: Int) {
        mActivity.addFragment(fragment, frameId)
    }


    /**
     * 替换fragment
     *
     * @param fragment
     * @param frameId
     */
    protected fun replaceFragment(fragment: BaseFragment, @IdRes frameId: Int) {
        mActivity.replaceFragment(fragment, frameId)
    }


    /**
     * 隐藏fragment
     *
     * @param fragment
     */
    protected fun hideFragment(fragment: BaseFragment) {
        mActivity.hideFragment(fragment)
    }


    /**
     * 显示fragment
     *
     * @param fragment
     */
    protected fun showFragment(fragment: BaseFragment) {
        mActivity.showFragment(fragment)
    }


    /**
     * 移除Fragment
     *
     * @param fragment
     */
    protected fun removeFragment(fragment: BaseFragment) {
        mActivity.removeFragment(fragment)
    }

}