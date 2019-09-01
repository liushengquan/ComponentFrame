package com.example.main.activity.contact

import android.content.Intent
import android.text.TextUtils
import com.example.common.activity.BaseActivity
import com.example.main.R
import com.example.main.activity.MainActivity
import com.example.main.activity.chat.MainChatActivity
import com.tencent.imsdk.TIMConversationType
import com.tencent.qcloud.tim.uikit.modules.chat.base.ChatInfo
import com.tencent.qcloud.tim.uikit.modules.contact.ContactItemBean
import com.tencent.qcloud.tim.uikit.modules.contact.FriendProfileLayout
import com.tencent.qcloud.tim.uikit.utils.TUIKitConstants


class MainFriendProfileActivity : BaseActivity() {


    override fun getContentView(): Int {
        return R.layout.main_activity_contact_friend_profile
    }

    override fun initView() {
        val layout = findViewById<FriendProfileLayout>(R.id.friend_profile)

        layout.initData(intent.getSerializableExtra(TUIKitConstants.ProfileType.CONTENT))
        layout.setOnButtonClickListener(object : FriendProfileLayout.OnButtonClickListener {
            override fun onStartConversationClick(info: ContactItemBean) {
                val chatInfo = ChatInfo()
                chatInfo.type = TIMConversationType.C2C
                chatInfo.id = info.id
                var chatName = info.id
                if (!TextUtils.isEmpty(info.remark)) {
                    chatName = info.remark
                } else if (!TextUtils.isEmpty(info.nickname)) {
                    chatName = info.nickname
                }
                chatInfo.chatName = chatName
                val intent = Intent(this@MainFriendProfileActivity, MainChatActivity::class.java)
                intent.putExtra("ChatInfo", chatInfo)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }

            override fun onDeleteFriendClick(id: String) {
                val intent = Intent(this@MainFriendProfileActivity, MainActivity::class.java)
                startActivity(intent)
            }
        })
    }

    override fun initData() {
    }

    override fun appInitCallback() {

    }
}