package com.example.main.activity.me

import com.example.common.fragment.BaseFragment
import com.example.main.R

class MainMeFragment : BaseFragment() {

    companion object {
        fun newInstance() = MainMeFragment()
    }

    override fun getContentView(): Int {
        return R.layout.main_fragment_me
    }

    override fun initView() {
    }

    override fun initData() {
    }
}