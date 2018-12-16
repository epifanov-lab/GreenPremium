package com.lab.greenpremium.ui.screen.main.portfolio

import com.lab.greenpremium.App
import com.lab.greenpremium.R
import com.lab.greenpremium.ui.screen.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_portfolio.*
import javax.inject.Inject

class PortfolioFragment : BaseFragment(), PortfolioContract.View {

    @Inject
    internal lateinit var presenter: PortfolioPresenter

    companion object {
        fun newInstance() = PortfolioFragment()
    }

    override fun initializeDaggerComponent() {
        DaggerPortfolioComponent.builder()
                .appComponent((activity?.application as App).component)
                .portfolioModule(PortfolioModule(this))
                .build()
                .inject(this)
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