package com.lab.greenpremium.ui.components.adapters


import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.lab.greenpremium.ui.screen.main.portfolio.PortfolioType
import com.lab.greenpremium.ui.screen.main.portfolio.sub.PortfolioSubFragment

class PortfolioPagerAdapter(fm: FragmentManager?, private val context: Context?) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {
        return PortfolioSubFragment.newInstance(position)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return PortfolioType.getTitles(context)[position]
    }

    override fun getCount(): Int {
        return PortfolioType.values().size
    }
}