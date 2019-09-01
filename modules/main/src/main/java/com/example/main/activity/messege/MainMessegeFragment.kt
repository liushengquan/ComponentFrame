package com.example.main.activity.messege

import android.content.Intent
import com.example.common.base.BaseApp
import com.example.common.fragment.BaseFragment
import com.example.main.R
import com.example.main.activity.chat.MainChatActivity
import com.tencent.imsdk.TIMConversationType
import com.tencent.qcloud.tim.uikit.component.TitleBarLayout
import com.tencent.qcloud.tim.uikit.modules.chat.base.ChatInfo
import com.tencent.qcloud.tim.uikit.modules.conversation.ConversationLayout
import com.tencent.qcloud.tim.uikit.modules.conversation.base.ConversationInfo


class MainMessegeFragment : BaseFragment() {

    private var mConversationLayout: ConversationLayout? = null
    private var mTitleBarLayout: TitleBarLayout? = null

    companion object {
        fun newInstance() = MainMessegeFragment()
    }

    override fun getContentView(): Int {
        return R.layout.main_fragment_message
    }

    override fun initView() {
        mConversationLayout = activity?.findViewById(R.id.conversation_layout)
        mConversationLayout?.initDefault()
        mConversationLayout?.conversationList?.setOnItemClickListener { view, position, messageInfo ->
            startChatActivity(messageInfo!!)
        }

        // 获取 TitleBarLayout
        mTitleBarLayout = mConversationLayout?.findViewById(R.id.conversation_title)
        // 响应菜单按钮的点击事件
        mTitleBarLayout?.setOnRightClickListener {

        }
    }

    private fun startChatActivity(conversationInfo: ConversationInfo) {
        val chatInfo = ChatInfo()
        chatInfo.type = if (conversationInfo.isGroup) TIMConversationType.Group else TIMConversationType.C2C
        chatInfo.id = conversationInfo.id
        chatInfo.chatName = conversationInfo.title
        val intent = Intent(BaseApp.getAppContext(), MainChatActivity::class.java)
        intent.putExtra("ChatInfo", chatInfo)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    override fun initData() {

    }
}