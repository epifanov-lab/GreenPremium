package com.lab.greenpremium.ui.screen.main.favorites

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.LinearLayout
import com.lab.greenpremium.App
import com.lab.greenpremium.R
import com.lab.greenpremium.data.UserModel
import com.lab.greenpremium.data.entity.Product
import com.lab.greenpremium.ui.screen.base.BaseFragment
import com.lab.greenpremium.ui.screen.main.plants.sub.PlantRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_favorites.*
import javax.inject.Inject


class FavoritesFragment : BaseFragment(), FavoritesContract.View, PlantRecyclerAdapter.OnProductSelectedListener {

    @Inject
    internal lateinit var presenter: FavoritesPresenter

    lateinit var list: List<Product>

    companion object {
        fun newInstance() = FavoritesFragment()
    }

    override fun initializeDaggerComponent() {
        DaggerFavoritesComponent.builder()
                .appComponent((activity?.application as App).component)
                .favoritesModule(FavoritesModule(this))
                .build()
                .inject(this)
    }

    override fun layoutResId(): Int {
        return R.layout.fragment_favorites
    }

    override fun initViews() {
         list= UserModel.getFavoritesProductsList()
        initializeList()
    }

    private fun initializeList() {
        if (list.isNotEmpty()) {
            label_empty_list.visibility = View.GONE
            recycler_plants.visibility = View.VISIBLE
            recycler_plants.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
            recycler_plants.adapter = PlantRecyclerAdapter(list, context?.resources?.getDimension(R.dimen.space_24)?.toInt(), this)
        } else {
            label_empty_list.visibility = View.VISIBLE
            recycler_plants.visibility = View.GONE
        }
    }

    override fun onProductSelected(product: Product) {
        //TODO
    }

    override fun onResume() {
        super.onResume()
        recycler_plants.adapter?.notifyDataSetChanged()
    }
}