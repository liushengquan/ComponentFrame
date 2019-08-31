package com.example.api.appinit

import com.example.api.report.IBaseService
import com.tencent.qcloud.tim.uikit.base.IUIKitCallBack

interface IAppInitService : IBaseService {
    fun loginIm(userid: String, usersig: String, callback: IUIKitCallBack)
}