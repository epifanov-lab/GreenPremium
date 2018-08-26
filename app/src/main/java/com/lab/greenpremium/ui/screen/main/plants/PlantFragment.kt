package com.lab.greenpremium.ui.screen.main.plants

import com.lab.greenpremium.R
import com.lab.greenpremium.ui.components.adapters.PlantsPagerAdapter
import com.lab.greenpremium.ui.screen.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_plants.*

class PlantFragment : BaseFragment() {

    companion object {
        fun newInstance() = PlantFragment()
    }

    override fun initializeDaggerComponent() {
        //TODO impl
    }

    override fun layoutResId(): Int {
        return R.layout.fragment_plants
    }

    override fun initViews() {
        initializeTabs()
    }

    private fun initializeTabs() {
        pager.adapter = PlantsPagerAdapter(activity?.supportFragmentManager, context)
        tab_layout.setupWithViewPager(pager)
    }
}