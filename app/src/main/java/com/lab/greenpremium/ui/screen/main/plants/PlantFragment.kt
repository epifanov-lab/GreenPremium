package com.lab.greenpremium.ui.screen.main.plants

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lab.greenpremium.R


class PlantFragment : Fragment() {

    companion object {
        val TAG: String = PlantFragment::class.java.simpleName
        fun newInstance() = PlantFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_plants, container, false)
    }
}