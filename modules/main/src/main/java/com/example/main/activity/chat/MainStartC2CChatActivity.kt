package com.example.main.activity.chat

import android.content.Intent
import android.text.TextUtils
import android.view.View
import com.example.common.activity.BaseActivity
import com.example.main.R
import com.tencent.imsdk.TIMConversationType
import com.tencent.qcloud.tim.uikit.base.ITitleBarLayout
import com.tencent.qcloud.tim.uikit.component.TitleBarLayout
import com.tencent.qcloud.tim.uikit.modules.chat.base.ChatInfo
import com.tencent.qcloud.tim.uikit.modules.contact.ContactItemBean
import com.tencent.qcloud.tim.uikit.modules.contact.ContactListView
import com.tencent.qcloud.tim.uikit.utils.ToastUtil


class MainStartC2CChatActivity : BaseActivity() {
    private val TAG = MainStartC2CChatActivity::class.java!!.getSimpleName()

    private var mTitleBar: TitleBarLayout? = null
    private var mContactListView: ContactListView? = null
    private var mSelectedItem: ContactItemBean? = null
    private val mContacts = mutableListOf<ContactItemBean>()

    override fun getContentView(): Int {
        return R.layout.main_activity_start_c2c_chat
    }

    override fun initView() {
        mTitleBar = findViewById<View>(R.id.start_c2c_chat_title) as TitleBarLayout?
        mTitleBar?.setTitle(resources.getString(R.string.sure), ITitleBarLayout.POSITION.RIGHT)
        mTitleBar?.rightTitle?.setTextColor(resources.getColor(R.color.title_bar_font_color))
        mTitleBar?.rightIcon?.visibility = View.GONE
        mTitleBar?.setOnRightClickListener { startConversation() }
        mTitleBar?.setOnLeftClickListener { finish() }

        mContactListView = findViewById<View>(R.id.contact_list_view) as ContactListView?
        mContactListView?.setSingleSelectMode(true)
        mContactListView?.loadDataSource(ContactListView.DataSource.FRIEND_LIST)
        mContactListView?.setOnSelectChangeListener { contact, selected ->
            if (selected) {
                if (mSelectedItem === contact) {
                    // 相同的Item，忽略
                } else {
                    if (mSelectedItem != null) {
                        mSelectedItem?.isSelected = false
                    }
                    mSelectedItem = contact
                }
            } else {
                if (mSelectedItem === contact) {
                    mSelectedItem?.isSelected = false
                }
            }
        }
    }

    override fun initData() {
    }

    fun startConversation() {
        if (mSelectedItem == null || !mSelectedItem!!.isSelected) {
            ToastUtil.toastLongMessage("请选择聊天对象")
            return
        }
        val chatInfo = ChatInfo()
        chatInfo.type = TIMConversationType.C2C
        chatInfo.id = mSelectedItem?.id
        var chatName = mSelectedItem?.id
        if (!TextUtils.isEmpty(mSelectedItem?.remark)) {
            chatName = mSelectedItem?.remark
        } else if (!TextUtils.isEmpty(mSelectedItem?.nickname)) {
            chatName = mSelectedItem?.nickname
        }
        chatInfo.chatName = chatName
        val intent = Intent(this@MainStartC2CChatActivity, MainChatActivity::class.java)
        intent.putExtra("ChatInfo", chatInfo)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }

    override fun appInitCallback() {
    }
}