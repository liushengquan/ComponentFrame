package com.example.main.activity.chat

import android.graphics.drawable.ColorDrawable
import com.example.common.activity.BaseActivity
import com.example.main.R
import com.tencent.qcloud.tim.uikit.modules.chat.ChatLayout
import com.tencent.qcloud.tim.uikit.modules.chat.base.ChatInfo


class MainChatActivity : BaseActivity() {

    private var mChatLayout: ChatLayout? = null

    override fun getContentView(): Int {
        return R.layout.main_activity_chat
    }

    override fun initView() {
        // 从布局文件中获取聊天面板
        mChatLayout = findViewById(R.id.chat_layout)
        // 单聊面板的默认 UI 和交互初始化
        mChatLayout?.initDefault()
    }

    override fun initData() {
        val chatInfo = intent.extras.getSerializable("ChatInfo") as ChatInfo
        // 传入 ChatInfo 的实例，这个实例必须包含必要的聊天信息，一般从调用方传入
        mChatLayout?.setChatInfo(chatInfo)

        //获取单聊面板的标题栏
        val titleBar = mChatLayout?.titleBar

        //单聊面板标记栏返回按钮点击事件，这里需要开发者自行控制
        titleBar?.setOnLeftClickListener {
            finish()
        }

        val messageLayout = mChatLayout?.messageLayout
        messageLayout?.background = ColorDrawable(-0x4f1d0100)
        // 设置默认头像，默认与朋友与自己的头像相同
        messageLayout?.avatar = R.drawable.main_icon_avator
        // 设置头像圆角，不设置则默认不做圆角处理
        messageLayout?.avatarRadius = 50
        // 设置头像大小
        messageLayout?.avatarSize = intArrayOf(48, 48)
    }

    override fun onDestroy() {
        super.onDestroy()
        mChatLayout?.exitChat()
    }

    override fun appInitCallback() {
    }
}