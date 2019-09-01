package com.example.api.im

import com.example.api.report.IBaseService
import com.tencent.imsdk.TIMCallBack
import com.tencent.imsdk.TIMValueCallBack
import com.tencent.imsdk.friendship.TIMFriendRequest
import com.tencent.imsdk.friendship.TIMFriendResult
import com.tencent.qcloud.tim.uikit.base.IUIKitCallBack

interface IImService : IBaseService {
    fun loginIm(userid: String, usersig: String, callback: IUIKitCallBack)
    fun addFriend(request: TIMFriendRequest, callback: TIMValueCallBack<TIMFriendResult>)
    fun joinGroup(groupId: String, desc: String, callback: TIMCallBack)
}