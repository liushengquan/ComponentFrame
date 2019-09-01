package com.example.main.activity.contact

import android.content.Intent
import com.example.common.fragment.BaseFragment
import com.example.main.R
import com.tencent.qcloud.tim.uikit.modules.contact.ContactLayout
import com.tencent.qcloud.tim.uikit.utils.TUIKitConstants



class MainContactFragment : BaseFragment() {

    companion object {
        fun newInstance() = MainContactFragment()
    }

    private var mContactLayout: ContactLayout? = null

    override fun getContentView(): Int {
        return R.layout.main_fragment_address
    }

    override fun initView() {
        mContactLayout = activity?.findViewById(R.id.contact_layout)

        mContactLayout?.titleBar?.setOnRightClickListener { }

        mContactLayout?.contactListView?.setOnItemClickListener { position, contact ->
            when (position) {
                0 -> {
                    val intent = Intent(this@MainContactFragment.activity, MainNewFriendActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
                1 -> {
                    val intent = Intent(this@MainContactFragment.activity, MainGroupListActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
                2 -> {
                    val intent = Intent(this@MainContactFragment.activity, MainBlackListActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
                else -> {
                    val intent = Intent(this@MainContactFragment.activity, MainFriendProfileActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    intent.putExtra(TUIKitConstants.ProfileType.CONTENT, contact)
                    startActivity(intent)
                }
            }
        }
    }

    override fun initData() {
    }

    override fun onResume() {
        super.onResume()
        refreshData()
    }

    private fun refreshData() {
        // 通讯录面板的默认UI和交互初始化
        mContactLayout?.initDefault()
    }
}