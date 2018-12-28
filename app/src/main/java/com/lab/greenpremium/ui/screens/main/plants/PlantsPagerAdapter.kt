package com.lab.greenpremium.ui.screens.main.plants


import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.lab.greenpremium.data.entity.Section
import com.lab.greenpremium.ui.screens.main.plants.sub.PlantsSubFragment

class PlantsPagerAdapter(fm: FragmentManager?,
                         private val sections: List<Section>) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {
        return PlantsSubFragment.newInstance(position)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return sections[position].name
    }

    override fun getCount(): Int {
        return sections.size
    }

}