package com.example.main.activity.address

import com.example.common.fragment.BaseFragment
import com.example.main.R
import com.tencent.qcloud.tim.uikit.modules.contact.ContactLayout

class MainAddressFragment : BaseFragment() {

    companion object {
        fun newInstance() = MainAddressFragment()
    }

    private var mContactLayout: ContactLayout? = null

    override fun getContentView(): Int {
        return R.layout.main_fragment_address
    }

    override fun initView() {
        mContactLayout = activity?.findViewById(R.id.contact_layout)
        mContactLayout?.initDefault()
    }

    override fun initData() {
    }
}