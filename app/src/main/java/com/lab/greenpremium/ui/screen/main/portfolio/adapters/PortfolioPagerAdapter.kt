package com.lab.greenpremium.ui.screen.main.portfolio.adapters


import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.lab.greenpremium.ui.screen.main.portfolio.PortfolioSubFragment

class PortfolioPagerAdapter(fm: FragmentManager?, private val context: Context?) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {
        return PortfolioSubFragment.newInstance(position)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return PortfolioSubFragment.PortfolioType.getTitles(context)[position]
    }

    override fun getCount(): Int {
        return PortfolioSubFragment.PortfolioType.values().size
    }
}