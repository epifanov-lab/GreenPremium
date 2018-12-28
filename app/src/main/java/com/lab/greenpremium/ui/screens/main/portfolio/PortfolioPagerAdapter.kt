package com.lab.greenpremium.ui.screens.main.portfolio


import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.lab.greenpremium.data.entity.PortfolioSection
import com.lab.greenpremium.ui.screens.main.portfolio.sub.PortfolioSubFragment

class PortfolioPagerAdapter(fm: FragmentManager?, private val context: Context?,
                            val portfolio: List<PortfolioSection>) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {
        return PortfolioSubFragment.newInstance(portfolio[position])
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return portfolio[position].name
    }

    override fun getCount(): Int {
        return portfolio.size
    }
}