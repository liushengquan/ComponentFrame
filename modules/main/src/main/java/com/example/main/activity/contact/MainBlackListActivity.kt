package com.example.main.activity.contact

import android.content.Intent
import android.view.View
import com.example.common.activity.BaseActivity
import com.example.main.R
import com.tencent.qcloud.tim.uikit.base.ITitleBarLayout
import com.tencent.qcloud.tim.uikit.component.TitleBarLayout
import com.tencent.qcloud.tim.uikit.modules.contact.ContactListView
import com.tencent.qcloud.tim.uikit.utils.TUIKitConstants

class MainBlackListActivity : BaseActivity() {
    private var mTitleBar: TitleBarLayout? = null
    private var mListView: ContactListView? = null

    override fun getContentView(): Int {
        return R.layout.main_activity_contact_blacklist
    }

    override fun initView() {
        mTitleBar = findViewById<View>(R.id.black_list_titlebar) as TitleBarLayout?
        mTitleBar?.setTitle(resources.getString(R.string.blacklist), ITitleBarLayout.POSITION.LEFT)
        mTitleBar?.setOnLeftClickListener { finish() }
        mTitleBar?.rightGroup?.visibility = View.GONE

        mListView = findViewById<View>(R.id.black_list) as ContactListView?
        mListView?.loadDataSource(ContactListView.DataSource.BLACK_LIST)
        mListView?.setOnItemClickListener { _, contact ->
            val intent = Intent(this@MainBlackListActivity, MainFriendProfileActivity::class.java)
            intent.putExtra(TUIKitConstants.ProfileType.CONTENT, contact)
            startActivity(intent)
        }
    }

    override fun initData() {
    }

    override fun onResume() {
        super.onResume()
        loadDataSource()
    }

    private fun loadDataSource() {
        mListView?.loadDataSource(ContactListView.DataSource.BLACK_LIST)
    }

    override fun appInitCallback() {
    }
}