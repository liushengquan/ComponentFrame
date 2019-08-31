package com.example.main.activity.messege

import com.example.common.fragment.BaseFragment
import com.example.main.R
import com.tencent.qcloud.tim.uikit.modules.conversation.ConversationLayout

class MainMessegeFragment : BaseFragment() {

    private var mConversationLayout: ConversationLayout? = null

    companion object {
        fun newInstance() = MainMessegeFragment()
    }

    override fun getContentView(): Int {
        return R.layout.main_fragment_message
    }

    override fun initView() {
        mConversationLayout = activity?.findViewById(R.id.conversation_layout)
        mConversationLayout?.initDefault()
    }

    override fun initData() {

    }
}