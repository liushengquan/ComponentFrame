package com.example.main.activity.contact

import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.common.adapter.BaseRecycleViewAdapter
import com.example.main.R
import com.tencent.imsdk.TIMFriendshipManager
import com.tencent.imsdk.TIMValueCallBack
import com.tencent.imsdk.friendship.TIMFriendPendencyItem
import com.tencent.imsdk.friendship.TIMFriendResponse
import com.tencent.imsdk.friendship.TIMFriendResult
import com.tencent.imsdk.friendship.TIMPendencyType
import com.tencent.mars.xlog.Log
import com.tencent.qcloud.tim.uikit.utils.ToastUtil


class MainNewFriendAdapter : BaseRecycleViewAdapter<TIMFriendPendencyItem>() {
    private val TAG = MainNewFriendAdapter::class.java!!.simpleName


    override fun hasHeaderView(): Boolean {
        return false
    }

    override fun hasFooterView(): Boolean {
        return false
    }

    override fun onCreateItemViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.main_item_new_friend, parent, false)
        return ViewHolder(view)
    }

    override fun onCreateHeaderViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder? {
        return null
    }

    override fun onCreateFooterViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder? {
        return null
    }

    override fun onBindItemViewHolder(viewHolder: RecyclerView.ViewHolder?, position: Int, data: TIMFriendPendencyItem) {
        val data = mDatas[position]
        val holder = viewHolder as ViewHolder
        holder.avatar?.setImageResource(R.drawable.ic_personal_member)
        holder.name?.text = if (TextUtils.isEmpty(data.nickname)) data.identifier else data.nickname
        holder.des?.text = data.addWording
        when (data.type) {
            TIMPendencyType.TIM_PENDENCY_COME_IN -> {
                holder.agree?.text = "同意"
                holder.agree?.setOnClickListener { v ->
                    val vv = v as TextView
                    doResponse(vv, data)
                }
            }
            TIMPendencyType.TIM_PENDENCY_SEND_OUT -> holder.agree?.text = "等待同意"
            TIMPendencyType.TIM_PENDENCY_BOTH -> holder.agree?.text = "已接受"
        }
    }

    private fun doResponse(view: TextView, data: TIMFriendPendencyItem) {
        val response = TIMFriendResponse()
        response.identifier = data.identifier
        response.responseType = TIMFriendResponse.TIM_FRIEND_RESPONSE_AGREE_AND_ADD
        TIMFriendshipManager.getInstance().doResponse(response, object : TIMValueCallBack<TIMFriendResult> {
            override fun onError(i: Int, s: String) {
                Log.e(TAG, "deleteFriends err code = $i, desc = $s")
                ToastUtil.toastShortMessage("Error code = $i, desc = $s")
            }

            override fun onSuccess(timUserProfiles: TIMFriendResult) {
                Log.i(TAG, "deleteFriends success")
                view.text = "已接受"
            }
        })
    }

    override fun onBindHeaderViewHolder(viewHolder: RecyclerView.ViewHolder?) {
    }

    override fun onBindFooterViewHolder(viewHolder: RecyclerView.ViewHolder?) {
    }

    inner class ViewHolder(rootView: View) : RecyclerView.ViewHolder(rootView) {
        val avatar: ImageView? = rootView.findViewById(R.id.avatar)!!
        val name: TextView? = rootView.findViewById(R.id.name)!!
        val des: TextView? = rootView.findViewById(R.id.description)!!
        val agree: Button? = rootView.findViewById(R.id.agree)!!
    }

}