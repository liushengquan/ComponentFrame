package com.example.main.activity.me

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.asynclayoutinflater.R.id.text
import android.text.TextUtils
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.main.R
import com.tencent.imsdk.*
import com.tencent.qcloud.tim.uikit.base.ITitleBarLayout
import com.tencent.qcloud.tim.uikit.component.LineControllerView
import com.tencent.qcloud.tim.uikit.component.SelectionActivity
import com.tencent.qcloud.tim.uikit.component.TitleBarLayout
import com.tencent.qcloud.tim.uikit.component.picture.imageEngine.impl.GlideEngine
import com.tencent.qcloud.tim.uikit.utils.TUIKitConstants
import com.tencent.qcloud.tim.uikit.utils.ToastUtil
import java.util.*
import kotlin.collections.HashMap


class ProfileLayout : LinearLayout, View.OnClickListener {

    private val TAG = ProfileLayout::class.java.simpleName

    private var mUserIcon: ImageView? = null
    private var mAccountView: TextView? = null
    private var mTitleBar: TitleBarLayout? = null

    private var mModifyUserIconView: LineControllerView? = null
    private var mModifyNickNameView: LineControllerView? = null
    private var mModifyAllowTypeView: LineControllerView? = null
    private var mModifySignatureView: LineControllerView? = null
    private var mAboutIM: LineControllerView? = null
    private val mJoinTypeTextList = mutableListOf<String>()
    private val mJoinTypeIdList = mutableListOf<String>()
    private var mJoinTypeIndex = 2
    private var mIconUrl: String? = null
    private var mContext: Context

    init {
        init()
    }

    constructor(context: Context) : super(context) {
        mContext = context
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        mContext = context
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr) {
        mContext = context
    }

    private fun init() {
        View.inflate(context, R.layout.main_profile_layout, this)

        mUserIcon = findViewById<View>(R.id.self_icon) as ImageView?
        val profile = TIMFriendshipManager.getInstance().querySelfProfile()
        if (profile != null) {
            if (!TextUtils.isEmpty(profile.faceUrl)) {
                GlideEngine.loadImage(mUserIcon, Uri.parse(profile.faceUrl))
            }
        } else {
            val shareInfo = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
            val url = shareInfo.getString("Icon_url", "")
            if (!TextUtils.isEmpty(url)) {
                GlideEngine.loadImage(mUserIcon, Uri.parse(url))
            }
        }

        mAccountView = findViewById<View>(R.id.self_account) as TextView?

        mTitleBar = findViewById<View>(R.id.self_info_title_bar) as TitleBarLayout?
        mTitleBar?.leftGroup?.visibility = View.GONE
        mTitleBar?.rightGroup?.visibility = View.GONE
        mTitleBar?.setTitle(resources.getString(R.string.profile), ITitleBarLayout.POSITION.MIDDLE)

        mModifyUserIconView = findViewById<View>(R.id.modify_user_icon) as LineControllerView?
        mModifyUserIconView?.setCanNav(false)
        mModifyUserIconView?.setOnClickListener(this)
        mModifySignatureView = findViewById<View>(R.id.modify_signature) as LineControllerView?
        mModifySignatureView?.setCanNav(true)
        mModifySignatureView?.setOnClickListener(this)
        mModifyNickNameView = findViewById<View>(R.id.modify_nick_name) as LineControllerView?
        mModifyNickNameView?.setCanNav(true)
        mModifyNickNameView?.setOnClickListener(this)
        mModifyAllowTypeView = findViewById<View>(R.id.modify_allow_type) as LineControllerView?
        mModifyAllowTypeView?.setCanNav(true)
        mModifyAllowTypeView?.setOnClickListener(this)
        mAboutIM = findViewById<View>(R.id.about_im) as LineControllerView?
        mAboutIM?.setCanNav(true)
        mAboutIM?.setOnClickListener(this)

        mJoinTypeTextList.add(resources.getString(R.string.allow_type_allow_any))
        mJoinTypeTextList.add(resources.getString(R.string.allow_type_deny_any))
        mJoinTypeTextList.add(resources.getString(R.string.allow_type_need_confirm))
        mJoinTypeIdList.add(TIMFriendAllowType.TIM_FRIEND_ALLOW_ANY)
        mJoinTypeIdList.add(TIMFriendAllowType.TIM_FRIEND_DENY_ANY)
        mJoinTypeIdList.add(TIMFriendAllowType.TIM_FRIEND_NEED_CONFIRM)

        mAccountView?.text = String.format(resources.getString(R.string.id), TIMManager.getInstance().loginUser)
        TIMFriendshipManager.getInstance().getSelfProfile(object : TIMValueCallBack<TIMUserProfile> {
            override fun onError(i: Int, s: String) {
                Log.e(TAG, "initSelfProfile err code = $i, desc = $s")
                ToastUtil.toastShortMessage("Error code = $i, desc = $s")
            }

            override fun onSuccess(profile: TIMUserProfile) {
                Log.i(TAG, "initSelfProfile success, timUserProfile = " + profile.toString())
                mModifyNickNameView?.content = profile.nickName
                mAccountView?.text = String.format(resources.getString(R.string.id), profile.identifier)
                mModifySignatureView?.content = profile.selfSignature
                mModifyAllowTypeView?.content = resources.getString(R.string.allow_type_need_confirm)
                when {
                    TextUtils.equals(TIMFriendAllowType.TIM_FRIEND_ALLOW_ANY, profile.allowType) -> {
                        mModifyAllowTypeView?.content = resources.getString(R.string.allow_type_allow_any)
                        mJoinTypeIndex = 0
                    }
                    TextUtils.equals(TIMFriendAllowType.TIM_FRIEND_DENY_ANY, profile.allowType) -> {
                        mModifyAllowTypeView?.content = resources.getString(R.string.allow_type_deny_any)
                        mJoinTypeIndex = 1
                    }
                    TextUtils.equals(TIMFriendAllowType.TIM_FRIEND_NEED_CONFIRM, profile.allowType) -> {
                        mModifyAllowTypeView?.content = resources.getString(R.string.allow_type_need_confirm)
                        mJoinTypeIndex = 2
                    }
                    else -> mModifyAllowTypeView?.content = profile.allowType
                }
            }
        })
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.modify_user_icon -> {
                mIconUrl = String.format("https://picsum.photos/id/%d/200/200", Random().nextInt(1000))
                GlideEngine.loadImage(mUserIcon, Uri.parse(mIconUrl))
                updateProfile()

                val shareInfo = context.getSharedPreferences("ChatInfo", Context.MODE_PRIVATE)
                val editor = shareInfo?.edit()
                editor?.putString("Icon_url", mIconUrl)
                editor?.commit()
            }
            R.id.modify_nick_name -> {
                val bundle = Bundle()
                bundle.putString(TUIKitConstants.Selection.TITLE, resources.getString(R.string.modify_nick_name))
                bundle.putString(TUIKitConstants.Selection.INIT_CONTENT, mModifyNickNameView?.content)
                bundle.putInt(TUIKitConstants.Selection.LIMIT, 20)
                SelectionActivity.startTextSelection(mContext, bundle) {
                    mModifyNickNameView?.content = text.toString()
                    updateProfile()
                }
            }
            R.id.modify_allow_type -> {
                val bundle2 = Bundle()
                bundle2.putString(TUIKitConstants.Selection.TITLE, resources.getString(R.string.add_rule))
                bundle2.putStringArrayList(TUIKitConstants.Selection.LIST, mJoinTypeTextList as ArrayList<String>?)
                bundle2.putInt(TUIKitConstants.Selection.DEFAULT_SELECT_ITEM_INDEX, mJoinTypeIndex)
                SelectionActivity.startListSelection(mContext, bundle2) {
                    mModifyAllowTypeView?.content = mJoinTypeTextList[text]
                    mJoinTypeIndex = text
                    updateProfile()
                }
            }
            R.id.modify_signature -> {
                val bundle3 = Bundle()
                bundle3.putString(TUIKitConstants.Selection.TITLE, resources.getString(R.string.modify_signature))
                bundle3.putString(TUIKitConstants.Selection.INIT_CONTENT, mModifySignatureView?.content)
                bundle3.putInt(TUIKitConstants.Selection.LIMIT, 20)
                SelectionActivity.startTextSelection(mContext, bundle3) {
                    mModifySignatureView?.content = text.toString()
                    updateProfile()
                }
            }
            R.id.about_im -> {
                /*val intent = Intent(context as Activity, WebViewActivit)
                (context).startActivity(intent)*/
            }
        }
    }

    private fun updateProfile() {
        val hashMap = HashMap<String, Any?>()

        // 头像
        if (!TextUtils.isEmpty(mIconUrl)) {
            hashMap[TIMUserProfile.TIM_PROFILE_TYPE_KEY_FACEURL] = mIconUrl
        }

        // 昵称
        val nickName = mModifyNickNameView?.content
        hashMap[TIMUserProfile.TIM_PROFILE_TYPE_KEY_NICK] = nickName

        // 个性签名
        val signature = mModifySignatureView?.content
        hashMap[TIMUserProfile.TIM_PROFILE_TYPE_KEY_SELFSIGNATURE] = signature

        // 地区
        hashMap.put(TIMUserProfile.TIM_PROFILE_TYPE_KEY_LOCATION, "gz")

        // 加我验证方式
        val allowType = mJoinTypeIdList?.get(mJoinTypeIndex)
        hashMap.put(TIMUserProfile.TIM_PROFILE_TYPE_KEY_ALLOWTYPE, allowType)

        TIMFriendshipManager.getInstance().modifySelfProfile(hashMap, object : TIMCallBack {
            override fun onSuccess() {
                Log.e(TAG, "modifySelfProfile Success")
            }

            override fun onError(p0: Int, p1: String?) {
                Log.e(TAG, "modifySelfProfile err code = $p0, desc = $p1")
                ToastUtil.toastShortMessage("Error code = code = $p0, desc = $p1")
            }
        })
    }

}