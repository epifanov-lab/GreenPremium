package com.lab.greenpremium.ui.screen.main.portfolio

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.StaggeredGridLayoutManager
import com.lab.greenpremium.R
import com.lab.greenpremium.KEY_OBJECT
import com.lab.greenpremium.ui.components.adapters.AsymmetricImageGridAdapter
import com.lab.greenpremium.ui.screen.base.BaseFragment
import com.lab.greenpremium.utills.getMockImageList
import kotlinx.android.synthetic.main.sub_fragment_portfolio.*


class PortfolioSubFragment : BaseFragment() {

    lateinit var type: PortfolioType

    companion object {
        fun newInstance(type: Int): PortfolioSubFragment {
            val fragment = PortfolioSubFragment()
            val args = Bundle()
            args.putInt(KEY_OBJECT, type)
            fragment.arguments = args
            return fragment
        }

    }

    override fun initializeDaggerComponent() {
        //TODO impl
    }

    override fun layoutResId(): Int {
        return R.layout.sub_fragment_portfolio
    }

    override fun initViews() {
        type = PortfolioType.values()[arguments!!.getInt(KEY_OBJECT)]

        //test.text = getString(type.titleResId)
        asymmetric_image_grid.layoutManager = StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL)
        asymmetric_image_grid.adapter = AsymmetricImageGridAdapter(context!!, getMockImageList(16, 1200, 600))
    }
}