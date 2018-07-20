package com.lab.greenpremium.ui.screen.main.basket

import com.lab.greenpremium.R
import com.lab.greenpremium.ui.base.BaseFragment


class BasketFragment : BaseFragment() {

    companion object {
        fun newInstance() = BasketFragment()
    }

    override fun initializeDaggerComponent() {
        //TODO impl
    }

    override fun layoutResId(): Int {
        return R.layout.fragment_basket
    }

    override fun initViews() {
        //TODO impl
    }
}