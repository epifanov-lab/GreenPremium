package com.lab.greenpremium.ui.screen.main.portfolio

import com.lab.greenpremium.R
import com.lab.greenpremium.ui.base.BaseFragment
import com.lab.greenpremium.ui.screen.main.plants.PortfolioPagerAdapter
import kotlinx.android.synthetic.main.fragment_portfolio.*


class PortfolioFragment : BaseFragment() {

    companion object {
        fun newInstance() = PortfolioFragment()
    }

    override fun initializeDaggerComponent() {
        //TODO impl
    }

    override fun layoutResId(): Int {
        return R.layout.fragment_portfolio
    }

    override fun initViews() {
        initializeTabs()
    }

    private fun initializeTabs() {
        pager.adapter = PortfolioPagerAdapter(activity?.supportFragmentManager, context)
        tab_layout.setupWithViewPager(pager)
    }
}