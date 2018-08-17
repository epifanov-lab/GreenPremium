package com.lab.greenpremium.ui.screen.main.portfolio

import android.os.Bundle
import com.lab.greenpremium.R
import com.lab.greenpremium.SCREEN_KEY
import com.lab.greenpremium.ui.screen.base.BaseFragment
import kotlinx.android.synthetic.main.sub_fragment_portfolio.*

class PortfolioSubFragment : BaseFragment() {

    lateinit var type: PortfolioType

    companion object {
        fun newInstance(type: Int): PortfolioSubFragment {
            val fragment = PortfolioSubFragment()
            val args = Bundle()
            args.putInt(SCREEN_KEY, type)
            fragment.arguments = args
            return fragment
        }

    }

    override fun initializeDaggerComponent() {
        //TODO impl
    }

    override fun layoutResId(): Int {
        return R.layout.sub_fragment_portfolio
    }

    override fun initViews() {
        type = PortfolioType.values()[arguments!!.getInt(SCREEN_KEY)]

        test.text = getString(type.titleResId)
    }
}