package com.example.main.activity.chat

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.common.activity.BaseActivity
import com.example.main.R
import com.tencent.imsdk.TIMConversationType
import com.tencent.imsdk.TIMManager
import com.tencent.qcloud.tim.uikit.base.ITitleBarLayout
import com.tencent.qcloud.tim.uikit.base.IUIKitCallBack
import com.tencent.qcloud.tim.uikit.component.LineControllerView
import com.tencent.qcloud.tim.uikit.component.SelectionActivity
import com.tencent.qcloud.tim.uikit.component.TitleBarLayout
import com.tencent.qcloud.tim.uikit.modules.chat.GroupChatManagerKit
import com.tencent.qcloud.tim.uikit.modules.chat.base.ChatInfo
import com.tencent.qcloud.tim.uikit.modules.contact.ContactListView
import com.tencent.qcloud.tim.uikit.modules.group.info.GroupInfo
import com.tencent.qcloud.tim.uikit.modules.group.member.GroupMemberInfo
import com.tencent.qcloud.tim.uikit.utils.TUIKitConstants
import com.tencent.qcloud.tim.uikit.utils.ToastUtil
import java.util.*


class MainStartGroupChatActivity : BaseActivity() {
    private val TAG = MainStartGroupChatActivity::class.java!!.getSimpleName()

    private var mTitleBar: TitleBarLayout? = null
    private var mContactListView: ContactListView? = null
    private var mJoinType: LineControllerView? = null
    private val mMembers = mutableListOf<GroupMemberInfo>()
    private var mGroupType = -1
    private var mJoinTypeIndex = 2
    private val mJoinTypes = mutableListOf<String>()
    private val mGroupTypeValue = mutableListOf<String>()
    private var mCreating: Boolean = false

    override fun getContentView(): Int {
        return R.layout.main_activity_start_group_chat
    }

    override fun initView() {
        var array = resources.getStringArray(R.array.group_type)
        mGroupTypeValue.addAll(array)
        array = resources.getStringArray(R.array.group_join_type)
        mJoinTypes.addAll(array)
        val memberInfo = GroupMemberInfo()
        memberInfo.account = TIMManager.getInstance().loginUser
        mMembers.add(0, memberInfo)
        mTitleBar = findViewById<View>(R.id.group_create_title_bar) as TitleBarLayout?
        mTitleBar?.setTitle(resources.getString(R.string.sure), ITitleBarLayout.POSITION.RIGHT)
        mTitleBar?.rightTitle?.setTextColor(resources.getColor(R.color.title_bar_font_color))
        mTitleBar?.rightIcon?.visibility = View.GONE
        mTitleBar?.setOnRightClickListener { createGroupChat() }
        mTitleBar?.setOnLeftClickListener { finish() }

        mJoinType = findViewById<View>(R.id.group_type_join) as LineControllerView?
        mJoinType?.setOnClickListener { showJoinTypePickerView() }
        mJoinType?.setCanNav(true)
        mJoinType?.content = mJoinTypes[2]

        mContactListView = findViewById<View>(R.id.group_create_member_list) as ContactListView?
        mContactListView?.loadDataSource(ContactListView.DataSource.FRIEND_LIST)
        mContactListView?.setOnSelectChangeListener { contact, selected ->
            if (selected) {
                val memberInfo = GroupMemberInfo()
                memberInfo.account = contact?.id
                mMembers.add(memberInfo)
            } else {
                for (i in mMembers.size - 1 downTo 0) {
                    if (mMembers[i].account == contact?.id) {
                        mMembers?.removeAt(i)
                    }
                }
            }
        }

        setGroupType(intent.getIntExtra("type", TUIKitConstants.GroupType.PRIVATE))
    }

    override fun initData() {
    }

    fun setGroupType(type: Int) {
        mGroupType = type
        when (type) {
            TUIKitConstants.GroupType.PUBLIC -> {
                mTitleBar?.setTitle(resources.getString(R.string.create_group_chat), ITitleBarLayout.POSITION.MIDDLE)
                mJoinType?.visibility = View.VISIBLE
            }
            TUIKitConstants.GroupType.CHAT_ROOM -> {
                mTitleBar?.setTitle(resources.getString(R.string.create_chat_room), ITitleBarLayout.POSITION.MIDDLE)
                mJoinType?.visibility = View.VISIBLE
            }
            TUIKitConstants.GroupType.PRIVATE -> {
                mTitleBar?.setTitle(resources.getString(R.string.create_private_group), ITitleBarLayout.POSITION.MIDDLE)
                mJoinType?.visibility = View.GONE
            }
            else -> {
                mTitleBar?.setTitle(resources.getString(R.string.create_private_group), ITitleBarLayout.POSITION.MIDDLE)
                mJoinType?.visibility = View.GONE
            }
        }
    }

    private fun showJoinTypePickerView() {
        val bundle = Bundle()
        bundle.putString(TUIKitConstants.Selection.TITLE, resources.getString(R.string.group_join_type))
        bundle.putStringArrayList(TUIKitConstants.Selection.LIST, mJoinTypes as ArrayList<String>?)
        bundle.putInt(TUIKitConstants.Selection.DEFAULT_SELECT_ITEM_INDEX, mJoinTypeIndex)
        SelectionActivity.startListSelection(this, bundle) { text ->
            mJoinType?.content = mJoinTypes[text as Int]
            mJoinTypeIndex = text
        }
    }

    private fun createGroupChat() {
        if (mCreating) {
            return
        }
        if (mGroupType < 3 && mMembers.size === 1) {
            ToastUtil.toastLongMessage(resources.getString(R.string.tips_empty_group_member))
            return
        }
        if (mGroupType > 0 && mJoinTypeIndex === -1) {
            ToastUtil.toastLongMessage(resources.getString(R.string.tips_empty_group_type))
            return
        }
        if (mGroupType === 0) {
            mJoinTypeIndex = -1
        }
        val groupInfo = GroupInfo()
        var groupName = TIMManager.getInstance().loginUser
        for (i in 1 until mMembers.size) {
            groupName = groupName + "、" + mMembers[i].account
        }
        if (groupName.length > 20) {
            groupName = groupName.substring(0, 17) + "..."
        }
        groupInfo.chatName = groupName
        groupInfo.groupName = groupName
        groupInfo.memberDetails = mMembers
        groupInfo.groupType = mGroupTypeValue[mGroupType]
        groupInfo.joinType = mJoinTypeIndex

        mCreating = true
        GroupChatManagerKit.createGroupChat(groupInfo, object : IUIKitCallBack {
            override fun onSuccess(data: Any) {
                val chatInfo = ChatInfo()
                chatInfo.type = TIMConversationType.Group
                chatInfo.id = data.toString()
                chatInfo.chatName = groupInfo.groupName
                val intent = Intent(this@MainStartGroupChatActivity, MainChatActivity::class.java)
                intent.putExtra("ChatInfo", chatInfo)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
            }

            override fun onError(module: String, errCode: Int, errMsg: String) {
                mCreating = false
                ToastUtil.toastLongMessage("createGroupChat fail:$errCode=$errMsg")
            }
        })
    }

    override fun appInitCallback() {
    }
}