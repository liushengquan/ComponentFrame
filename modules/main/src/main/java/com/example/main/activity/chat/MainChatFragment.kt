package com.example.main.activity.chat

import android.os.Bundle
import com.example.common.fragment.BaseFragment
import com.example.main.R
import com.tencent.qcloud.tim.uikit.modules.chat.ChatLayout
import com.tencent.qcloud.tim.uikit.modules.chat.base.ChatInfo


class MainChatFragment : BaseFragment() {

    private var mChatLayout: ChatLayout? = null

    companion object {

        fun newInstance(chatInfo: ChatInfo): BaseFragment {
            val args = Bundle()
            args.putSerializable("ChatInfo", chatInfo)
            val mainChatFragment = MainChatFragment()
            mainChatFragment.arguments = args
            return mainChatFragment!!
        }
    }

    override fun getContentView(): Int {
        return R.layout.main_fragment_chat
    }

    override fun initView() {
        // 从布局文件中获取聊天面板
        mChatLayout = activity?.findViewById(R.id.chat_layout)
        // 单聊面板的默认 UI 和交互初始化
        mChatLayout?.initDefault()
    }

    override fun initData() {
        val chatInfo = arguments?.getSerializable("ChatInfo") as ChatInfo
        // 传入 ChatInfo 的实例，这个实例必须包含必要的聊天信息，一般从调用方传入
        mChatLayout?.setChatInfo(chatInfo)
    }
}