package com.example.impl

import android.content.Context
import com.example.api.im.IImService
import com.tencent.imsdk.TIMCallBack
import com.tencent.imsdk.TIMFriendshipManager
import com.tencent.imsdk.TIMGroupManager
import com.tencent.imsdk.TIMValueCallBack
import com.tencent.imsdk.friendship.TIMFriendRequest
import com.tencent.imsdk.friendship.TIMFriendResult
import com.tencent.qcloud.tim.uikit.TUIKit
import com.tencent.qcloud.tim.uikit.base.IUIKitCallBack


class ImServiceImpl private constructor(val context: Context) : IImService {

    companion object {

        @Volatile
        var mImService: IImService? = null

        fun init(context: Context): IImService {
            if (mImService == null) {
                synchronized(ImServiceImpl::class) {
                    if (mImService == null) {
                        mImService = ImServiceImpl(context)
                    }
                }
            }
            return mImService!!
        }
    }

    override fun loginIm(userid: String, usersig: String, callback: IUIKitCallBack) {
        TUIKit.login(userid, usersig, callback)
    }

    override fun addFriend(request: TIMFriendRequest, callback: TIMValueCallBack<TIMFriendResult>) {
        TIMFriendshipManager.getInstance().addFriend(request, callback)
    }

    override fun joinGroup(groupId: String, desc: String, callback: TIMCallBack) {
        TIMGroupManager.getInstance().applyJoinGroup(groupId, desc, callback)
    }

    override fun onServiceStart() {
    }

    override fun onServiceStop() {
    }
}