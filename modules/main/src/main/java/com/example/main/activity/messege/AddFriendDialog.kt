package com.example.main.activity.messege

import android.text.TextUtils
import android.widget.Toast
import com.example.api.im.IImService
import com.example.common.base.BaseApp
import com.example.common.dialog.BaseDialogFragment
import com.example.common.router.Router
import com.example.main.R
import com.tencent.imsdk.TIMCallBack
import com.tencent.imsdk.TIMValueCallBack
import com.tencent.imsdk.friendship.TIMFriendRequest
import com.tencent.imsdk.friendship.TIMFriendResult
import com.tencent.imsdk.friendship.TIMFriendStatus
import com.tencent.mars.xlog.Log
import com.tencent.qcloud.tim.uikit.utils.ToastUtil
import kotlinx.android.synthetic.main.main_dialog_add.*


class AddFriendDialog : BaseDialogFragment() {
    private val TAG = AddFriendDialog::class.simpleName

    private var mIsAddFriend = true

    companion object {
        fun newInstance() = AddFriendDialog()
    }

    override fun getContentView(): Int {
        return R.layout.main_dialog_add
    }

    override fun initData() {
        btn_concat_go_chat.setOnClickListener {
            if (mIsAddFriend) {
                addFriend()
            } else {
                addGroup()
            }
        }
        rg_add.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rb_friend -> mIsAddFriend = true
                R.id.rb_group -> mIsAddFriend = false
            }
        }
    }

    private fun addFriend() {
        val userId = et_concat_user_id.text?.toString()
        if (TextUtils.isEmpty(userId)) {
            Toast.makeText(BaseApp.getAppContext(), "请输入用户ID", Toast.LENGTH_LONG).show()
            return
        }

        val timFriendRequest = TIMFriendRequest(userId)
        timFriendRequest.addWording = et_concat_user_decs.text?.toString()
        timFriendRequest.addSource = "android"
        (Router.get().getService(IImService::class.simpleName!!) as IImService)
                .addFriend(timFriendRequest, object : TIMValueCallBack<TIMFriendResult> {
                    override fun onSuccess(p0: TIMFriendResult?) {
                        Log.i(TAG, "addFriend success result = ${p0.toString()}")
                        when (p0?.resultCode) {
                            TIMFriendStatus.TIM_FRIEND_STATUS_SUCC -> ToastUtil.toastShortMessage("成功")
                            TIMFriendStatus.TIM_FRIEND_PARAM_INVALID -> {
                                if (TextUtils.equals(p0.resultInfo, "Err_SNS_FriendAdd_Friend_Exist")) {
                                    ToastUtil.toastShortMessage("对方已是您的好友")
                                } else {
                                    ToastUtil.toastShortMessage("您的好友数已达系统上限")
                                }
                            }
                            TIMFriendStatus.TIM_ADD_FRIEND_STATUS_SELF_FRIEND_FULL -> ToastUtil.toastShortMessage("您的好友数已达系统上限")
                            TIMFriendStatus.TIM_ADD_FRIEND_STATUS_THEIR_FRIEND_FULL -> ToastUtil.toastShortMessage("对方的好友数已达系统上限")
                            TIMFriendStatus.TIM_ADD_FRIEND_STATUS_IN_SELF_BLACK_LIST -> ToastUtil.toastShortMessage("被加好友在自己的黑名单中")
                            TIMFriendStatus.TIM_ADD_FRIEND_STATUS_FRIEND_SIDE_FORBID_ADD -> ToastUtil.toastShortMessage("对方已禁止加好友")
                            TIMFriendStatus.TIM_ADD_FRIEND_STATUS_IN_OTHER_SIDE_BLACK_LIST -> ToastUtil.toastShortMessage("您已被被对方设置为黑名单")
                            TIMFriendStatus.TIM_ADD_FRIEND_STATUS_PENDING -> ToastUtil.toastShortMessage("等待好友审核同意")
                            else -> ToastUtil.toastLongMessage("${p0?.resultCode} ${p0?.resultInfo}")
                        }
                        dialog.dismiss()
                    }

                    override fun onError(p0: Int, p1: String?) {
                        Log.e(TAG, "addFriend err code = $p0, desc = $p1")
                        ToastUtil.toastShortMessage("Error code = $p0, desc = $p1")
                        dialog.dismiss()
                    }
                })
    }

    private fun addGroup() {
        val groupId = et_concat_user_id.text?.toString()
        if (TextUtils.isEmpty(groupId)) {
            Toast.makeText(BaseApp.getAppContext(), "请输入组ID", Toast.LENGTH_LONG).show()
            return
        }

        (Router.get().getService(IImService::class.simpleName!!) as IImService)
                .joinGroup(groupId!!, et_concat_user_decs.text?.toString()!!, object : TIMCallBack {
                    override fun onError(p0: Int, p1: String?) {
                        Log.e(TAG, "addFriend err code = $p0, desc = $p1")
                        ToastUtil.toastShortMessage("Error code = $p0, desc = $p1")
                        dialog.dismiss()
                    }

                    override fun onSuccess() {
                        ToastUtil.toastShortMessage("加群请求已发送")
                        dialog.dismiss()
                    }
                })
    }
}