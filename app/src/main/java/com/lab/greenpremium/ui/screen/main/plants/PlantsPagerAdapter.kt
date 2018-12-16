package com.lab.greenpremium.ui.screen.main.plants


import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.lab.greenpremium.data.entity.raw.Plant
import com.lab.greenpremium.data.entity.raw.Plant.Type.Companion.getTitles
import com.lab.greenpremium.ui.screen.main.plants.sub.PlantsSubFragment

class PlantsPagerAdapter(fm: FragmentManager?, private val context: Context?) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {
        return PlantsSubFragment.newInstance(position)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return getTitles(context)[position]
    }

    override fun getCount(): Int {
        return Plant.Type.values().size
    }

}