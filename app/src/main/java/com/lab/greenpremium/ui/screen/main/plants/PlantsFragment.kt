package com.lab.greenpremium.ui.screen.main.plants

import com.lab.greenpremium.App
import com.lab.greenpremium.R
import com.lab.greenpremium.data.entity.Section
import com.lab.greenpremium.ui.screen.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_plants.*
import javax.inject.Inject

class PlantsFragment : BaseFragment(), PlantsContract.View {

    @Inject
    internal lateinit var presenter: PlantsPresenter

    companion object {
        fun newInstance() = PlantsFragment()
    }

    override fun initializeDaggerComponent() {
        DaggerPlantsComponent.builder()
                .appComponent((activity?.application as App).component)
                .plantsModule(PlantsModule(this))
                .build()
                .inject(this)
    }

    override fun layoutResId(): Int {
        return R.layout.fragment_plants
    }

    override fun initViews() {
        presenter.onViewCreated()
    }

    override fun initializeTabs(sections: List<Section>) {
        pager.adapter = PlantsPagerAdapter(activity?.supportFragmentManager, sections)
        tab_layout.setupWithViewPager(pager)
    }
}