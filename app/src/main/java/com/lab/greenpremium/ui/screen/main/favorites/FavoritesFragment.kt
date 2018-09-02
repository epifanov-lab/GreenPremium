package com.lab.greenpremium.ui.screen.main.favorites

import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import com.lab.greenpremium.R
import com.lab.greenpremium.data.repository.user.UserRepository
import com.lab.greenpremium.ui.components.adapters.PlantRecyclerAdapter
import com.lab.greenpremium.ui.screen.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_favorites.*


class FavoritesFragment : BaseFragment() {

    companion object {
        fun newInstance() = FavoritesFragment()
    }

    override fun initializeDaggerComponent() {
        //TODO impl
    }

    override fun layoutResId(): Int {
        return R.layout.fragment_favorites
    }

    override fun initViews() {
        recycler_plants.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
        recycler_plants.adapter = PlantRecyclerAdapter(UserRepository.plants.filter { it.isFavorite },
                context?.resources?.getDimension(R.dimen.space_medium_2)?.toInt(), this)
    }
}