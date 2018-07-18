package com.lab.greenpremium.ui.screen.main.portfolio

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lab.greenpremium.R


class PortfolioFragment : Fragment() {

    companion object {
        val TAG: String = PortfolioFragment::class.java.simpleName
        fun newInstance() = PortfolioFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_portfolio, container, false)
    }
}