package com.example.main.activity.me

import android.view.View
import android.widget.Button
import com.example.common.fragment.BaseFragment
import com.example.main.R
import com.example.main.activity.MainActivity
import com.tencent.imsdk.TIMCallBack
import com.tencent.imsdk.TIMManager
import com.tencent.qcloud.tim.uikit.TUIKit
import com.tencent.qcloud.tim.uikit.component.dialog.TUIKitDialog
import com.tencent.qcloud.tim.uikit.utils.ToastUtil


class MainMeFragment : BaseFragment() {

    private val mBaseView: View? = null
    private var mProfileLayout: ProfileLayout? = null

    companion object {
        fun newInstance() = MainMeFragment()
    }

    override fun getContentView(): Int {
        return R.layout.main_fragment_me
    }

    override fun initView() {
        mProfileLayout = mBaseView?.findViewById(R.id.profile_view)
        mBaseView?.findViewById<Button>(R.id.logout_btn)?.setOnClickListener {
            TUIKitDialog(activity!!)
                    .builder()
                    .setCancelable(true)
                    .setCancelOutside(true)
                    .setTitle("您确定要退出登录么？")
                    .setDialogWidth(0.75f)

                    .setPositiveButton("确定") {
                        TIMManager.getInstance().logout(object : TIMCallBack {
                            override fun onError(code: Int, desc: String) {
                                ToastUtil.toastLongMessage("logout fail: $code=$desc")
                            }

                            override fun onSuccess() {
                                val activity = activity
                                if (activity is MainActivity) {
                                    activity.logout(false)
                                }
                                TUIKit.unInit()
                                if (getActivity() != null) {
                                    getActivity()!!.finish()
                                }
                            }
                        })
                    }
                    .setNegativeButton("取消") { }
                    .show()
        }
    }

    override fun initData() {
    }
}