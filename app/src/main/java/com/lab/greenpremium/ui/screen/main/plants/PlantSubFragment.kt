package com.lab.greenpremium.ui.screen.main.plants

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import com.lab.greenpremium.KEY_TYPE
import com.lab.greenpremium.R
import com.lab.greenpremium.ui.base.BaseFragment
import com.lab.greenpremium.ui.screen.main.plants.adapters.PlantRecyclerAdapter
import com.lab.greenpremium.utills.getMockPlantList
import kotlinx.android.synthetic.main.sub_fragment_plants.*
import java.io.Serializable

class PlantSubFragment : BaseFragment() {

    enum class PlantType(var titleResId: Int) : Serializable {
        LIVING(R.string.title_plants_living),
        ARTIFICIAL(R.string.title_plants_artificial),
        BIG(R.string.title_plants_big);

        companion object {
            fun getTitles(context : Context?) : Array<String> {
                val titles : Array<String> = Array(PlantType.values().size) { "" }
                enumValues<PlantType>().forEach { titles[it.ordinal] = context!!.getString(it.titleResId) }
                return titles
            }
        }
    }

    companion object {
        fun newInstance(type: Int): PlantSubFragment {
            val fragment = PlantSubFragment()
            val args = Bundle()
            args.putInt(KEY_TYPE, type)
            fragment.arguments = args
            return fragment
        }
    }

    lateinit var type: PlantType

    override fun initializeDaggerComponent() {
        //TODO impl
    }

    override fun layoutResId(): Int {
        return R.layout.sub_fragment_plants
    }

    override fun initViews() {
        type = PlantType.values()[arguments!!.getInt(KEY_TYPE)]

        recycler.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
        recycler.adapter = PlantRecyclerAdapter(getMockPlantList())
    }
}