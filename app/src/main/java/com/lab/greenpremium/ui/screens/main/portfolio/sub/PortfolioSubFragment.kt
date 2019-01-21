package com.lab.greenpremium.ui.screens.main.portfolio.sub

import android.os.Bundle
import com.lab.greenpremium.App
import com.lab.greenpremium.KEY_OBJECT
import com.lab.greenpremium.R
import com.lab.greenpremium.data.entity.Photo
import com.lab.greenpremium.data.entity.PortfolioSection
import com.lab.greenpremium.data.entity.getPhotosUrls
import com.lab.greenpremium.ui.components.assimmetric_recycler.AsymmetricRecyclerViewAdapter
import com.lab.greenpremium.ui.components.assimmetric_recycler.custom.AgvPhotoClickListener
import com.lab.greenpremium.ui.components.assimmetric_recycler.custom.AgvPhotoItem
import com.lab.greenpremium.ui.components.assimmetric_recycler.custom.AgvPhotoRecyclerViewAdapter
import com.lab.greenpremium.ui.components.assimmetric_recycler.custom.SpacesItemDecoration
import com.lab.greenpremium.ui.screens.base.BaseActivity
import com.lab.greenpremium.ui.screens.base.BaseFragment
import kotlinx.android.synthetic.main.sub_fragment_portfolio.*
import javax.inject.Inject


class PortfolioSubFragment : BaseFragment(), PortfolioSubContract.View {

    @Inject
    internal lateinit var presenter: PortfolioSubPresenter

    companion object {
        fun newInstance(portfolioSection: PortfolioSection): PortfolioSubFragment {
            val fragment = PortfolioSubFragment()
            val args = Bundle()
            args.putSerializable(KEY_OBJECT, portfolioSection)
            fragment.arguments = args
            return fragment
        }
    }

    override fun initializeDaggerComponent() {
        DaggerPortfolioSubComponent.builder()
                .appComponent((activity?.application as App).component)
                .portfolioSubModule(PortfolioSubModule(this))
                .build()
                .inject(this)
    }

    override fun layoutResId(): Int {
        return R.layout.sub_fragment_portfolio
    }

    override fun initViews() {
        presenter.onViewCreated((arguments!!.getSerializable(KEY_OBJECT) as PortfolioSection).photos)
    }

    override fun initializeImagesGrid(photos: List<Photo>) {
        val agvPhotoItems: MutableList<AgvPhotoItem> = ArrayList()
        for ((index, photo) in photos.withIndex()) {
            val columnSpan = if (index == 0 || index % 3 == 0) 2 else 1
            val item = AgvPhotoItem(columnSpan, 1, index, photo)
            agvPhotoItems.add(item)
        }

        val adapter = AgvPhotoRecyclerViewAdapter(agvPhotoItems,
                AgvPhotoClickListener { index, photo -> presenter.onClickImage(index) })

        container_grid.setRequestedColumnCount(2)
        container_grid.addItemDecoration(SpacesItemDecoration(resources.getDimensionPixelSize(R.dimen.space_4)))
        container_grid.adapter = AsymmetricRecyclerViewAdapter(context, container_grid, adapter)
    }

    override fun goToGalleryScreen(photos: List<Photo>, pos: Int) {
        (activity as BaseActivity).goToGalleryScreen(getPhotosUrls(photos), pos)
    }
}