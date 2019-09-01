package com.example.common.dialog

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.util.DisplayMetrics
import android.view.*
import com.example.common.base.BaseApp


abstract class BaseDialogFragment : DialogFragment() {

    private var mGravity: Int = Gravity.CENTER
    private var mWidth = WindowManager.LayoutParams.WRAP_CONTENT
    private var mHeight = WindowManager.LayoutParams.WRAP_CONTENT
    private var mWidthScale = 1.0f

    override fun onStart() {
        super.onStart()
        val params = dialog.window.attributes
        params.gravity = Gravity.CENTER
        params.width = mWidth
        params.height = mHeight
        if (mWidthScale != 1.0f) {
            val dm = DisplayMetrics()
            activity?.windowManager?.defaultDisplay?.getMetrics(dm)
            params.width = (dm.widthPixels * mWidthScale).toInt()
        }
        dialog.window.attributes = params
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return LayoutInflater.from(BaseApp.getAppContext()).inflate(getContentView(), null, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
    }

    abstract fun getContentView(): Int
    abstract fun initData()

    protected fun setLocationAndSize(gravity: Int, width: Int, height: Int) {
        mGravity = gravity
        mWidth = width
        mHeight = height
    }

    protected fun setLocationAndSize(gravity: Int, widthScale: Float) {
        mGravity = gravity
        mWidthScale = widthScale
    }

}