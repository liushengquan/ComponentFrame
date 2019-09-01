package com.example.main.activity.contact

import android.content.Intent
import android.text.TextUtils
import android.view.View
import com.example.common.activity.BaseActivity
import com.example.main.R
import com.example.main.activity.chat.MainChatActivity
import com.example.main.activity.chat.MainStartGroupChatActivity
import com.example.main.activity.messege.AddFriendDialog
import com.tencent.imsdk.TIMConversationType
import com.tencent.qcloud.tim.uikit.base.ITitleBarLayout
import com.tencent.qcloud.tim.uikit.component.TitleBarLayout
import com.tencent.qcloud.tim.uikit.modules.chat.base.ChatInfo
import com.tencent.qcloud.tim.uikit.modules.contact.ContactListView


class MainGroupListActivity : BaseActivity() {

    private val TAG = MainGroupListActivity::class.java!!.getSimpleName()

    private var mTitleBar: TitleBarLayout? = null
    private var mListView: ContactListView? = null

    override fun getContentView(): Int {
        return R.layout.main_activity_group_list
    }

    override fun initView() {
        mTitleBar = findViewById<View>(R.id.group_list_titlebar) as TitleBarLayout?
        mTitleBar?.setTitle(resources.getString(R.string.group), ITitleBarLayout.POSITION.LEFT)
        mTitleBar?.setOnLeftClickListener { finish() }
        mTitleBar?.setTitle("添加组", ITitleBarLayout.POSITION.RIGHT)
        mTitleBar?.rightIcon?.visibility = View.GONE
        mTitleBar?.setOnRightClickListener {
//            addFriendOrGroup()
            val intent = Intent(this@MainGroupListActivity, MainStartGroupChatActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        mListView = findViewById<View>(R.id.group_list) as ContactListView?
        mListView?.loadDataSource(ContactListView.DataSource.GROUP_LIST)
        mListView?.setOnItemClickListener { _, contact ->
            val chatInfo = ChatInfo()
            chatInfo.type = TIMConversationType.Group
            var chatName = contact?.id
            if (!TextUtils.isEmpty(contact?.remark)) {
                chatName = contact?.remark
            } else if (!TextUtils.isEmpty(contact?.nickname)) {
                chatName = contact.nickname
            }
            chatInfo.chatName = chatName
            chatInfo.id = contact.id
            val intent = Intent(this@MainGroupListActivity, MainChatActivity::class.java)
            intent.putExtra("ChatInfo", chatInfo)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }

    override fun initData() {
    }

    private fun addFriendOrGroup() {
        val addFragment = AddFriendDialog.newInstance()
        addFragment.show(supportFragmentManager, "AddFriendDialog")
    }

    override fun onResume() {
        super.onResume()
        loadDataSource()
    }

    private fun loadDataSource() {
        mListView?.loadDataSource(ContactListView.DataSource.GROUP_LIST)
    }

    override fun appInitCallback() {
    }
}