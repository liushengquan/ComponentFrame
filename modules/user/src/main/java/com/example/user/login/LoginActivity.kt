package com.example.user.login

import android.widget.Toast
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.api.im.IImService
import com.example.common.activity.BaseActivity
import com.example.common.base.BaseApp
import com.example.common.router.Router
import com.example.common.router.constant.MainConstant
import com.example.common.router.constant.UserConstant
import com.example.user.R
import com.tencent.GenerateTestUserSig
import com.tencent.mars.xlog.Log
import com.tencent.qcloud.tim.uikit.base.IUIKitCallBack
import kotlinx.android.synthetic.main.user_activity_login.*

@Route(path = UserConstant.LoginActivity)
class LoginActivity : BaseActivity() {
    private val TAG = LoginActivity::class.simpleName

    override fun getContentView(): Int {
        return R.layout.user_activity_login
    }

    override fun initView() {
        Log.i(TAG, "initView")
        btn_user_login_sure.setOnClickListener {
            login()
        }
    }

    private fun login() {
        //登录腾讯IM
        val userId = et_user_login_account.text.toString()
        (Router.get().getService(IImService::class.simpleName!!) as IImService)
                .loginIm(userId, GenerateTestUserSig.genTestUserSig(userId), object : IUIKitCallBack {
                    override fun onSuccess(data: Any?) {
                        Log.i(TAG, "loginIm onSuccess ：%s ", data?.toString())
                        Toast.makeText(BaseApp.getAppContext(), "登录Im成功", Toast.LENGTH_LONG).show()
                        gotoMainActivity()
                    }

                    override fun onError(module: String?, errCode: Int, errMsg: String?) {
                        Log.i(TAG, "loginIm onError ：%s ", errMsg)
                    }
                })
    }

    private fun gotoMainActivity() {
        ARouter.getInstance().build(MainConstant.Mactivity)
                .navigation()
        finish()
    }

    override fun initData() {
    }

    override fun appInitCallback() {
    }
}