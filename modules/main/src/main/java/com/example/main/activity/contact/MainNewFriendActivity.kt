package com.example.main.activity.contact

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.example.common.activity.BaseActivity
import com.example.main.R
import com.example.main.activity.messege.AddFriendDialog
import com.tencent.imsdk.TIMFriendshipManager
import com.tencent.imsdk.TIMValueCallBack
import com.tencent.imsdk.friendship.TIMFriendPendencyItem
import com.tencent.imsdk.friendship.TIMFriendPendencyRequest
import com.tencent.imsdk.friendship.TIMFriendPendencyResponse
import com.tencent.imsdk.friendship.TIMPendencyType
import com.tencent.mars.xlog.Log
import com.tencent.qcloud.tim.uikit.base.ITitleBarLayout
import com.tencent.qcloud.tim.uikit.component.TitleBarLayout
import com.tencent.qcloud.tim.uikit.utils.ToastUtil


class MainNewFriendActivity : BaseActivity() {

    private val TAG = MainNewFriendActivity::class.java!!.simpleName

    private var mTitleBar: TitleBarLayout? = null
    private var mNewFriendLv: RecyclerView? = null
    private var mAdapter: MainNewFriendAdapter? = null
    private var mEmptyView: TextView? = null
    private val mList = mutableListOf<TIMFriendPendencyItem>()

    override fun getContentView(): Int {
        return R.layout.main_activity_new_friend
    }

    override fun initView() {
        mTitleBar = findViewById<View>(R.id.new_friend_titlebar) as TitleBarLayout?
        mTitleBar?.setTitle(resources.getString(R.string.new_friend), ITitleBarLayout.POSITION.LEFT)
        mTitleBar?.setOnLeftClickListener { finish() }
        mTitleBar?.setTitle("添加", ITitleBarLayout.POSITION.RIGHT)
        mTitleBar?.rightIcon?.visibility = View.GONE
        mTitleBar?.setOnRightClickListener { addFriendOrGroup() }

        mNewFriendLv = findViewById<View>(R.id.new_friend_list) as RecyclerView?
        mEmptyView = findViewById<View>(R.id.empty_text) as TextView?
    }

    private fun addFriendOrGroup() {
        val addFragment = AddFriendDialog.newInstance()
        addFragment.show(supportFragmentManager, "AddFriendDialog")
    }

    override fun initData() {
    }

    override fun appInitCallback() {
    }

    override fun onResume() {
        super.onResume()
        initPendency()
    }

    private fun initPendency() {
        val timFriendPendencyRequest = TIMFriendPendencyRequest()
        timFriendPendencyRequest.timPendencyGetType = TIMPendencyType.TIM_PENDENCY_COME_IN
        timFriendPendencyRequest.seq = 0
        timFriendPendencyRequest.timestamp = 0
        timFriendPendencyRequest.numPerPage = 10
        TIMFriendshipManager.getInstance().getPendencyList(timFriendPendencyRequest, object : TIMValueCallBack<TIMFriendPendencyResponse> {
            override fun onError(i: Int, s: String) {
                Log.e(TAG, "getPendencyList err code = $i, desc = $s")
                ToastUtil.toastShortMessage("Error code = $i, desc = $s")
            }

            override fun onSuccess(timFriendPendencyResponse: TIMFriendPendencyResponse) {
                Log.i(TAG, "getPendencyList success result = " + timFriendPendencyResponse.toString())
                if (timFriendPendencyResponse.items != null) {
                    if (timFriendPendencyResponse.items.size == 0) {
                        mEmptyView?.text = "没有新朋友"
                        mNewFriendLv?.visibility = View.GONE
                        mEmptyView?.visibility = View.VISIBLE
                        return
                    }
                }
                mNewFriendLv?.visibility = View.VISIBLE
                mList.clear()
                mList.addAll(timFriendPendencyResponse.items)
                mAdapter = MainNewFriendAdapter()
                mAdapter?.mDatas = mList
                mNewFriendLv?.adapter = mAdapter
                mAdapter?.notifyDataSetChanged()
            }
        })
    }

}