package com.lab.greenpremium.ui.screen.main.plants

import android.content.Context
import android.os.Bundle
import com.lab.greenpremium.KEY_TYPE
import com.lab.greenpremium.R
import com.lab.greenpremium.ui.base.BaseFragment
import kotlinx.android.synthetic.main.sub_fragment_plants.*
import java.io.Serializable

class PortfolioSubFragment : BaseFragment() {

    enum class PortfolioType(var titleResId: Int) : Serializable {
        LIVING(R.string.title_plants_living),
        ARTIFICIAL(R.string.title_plants_artificial),
        BIG(R.string.title_plants_big),
        SERVICE(R.string.title_service);

        companion object {
            fun getTitles(context : Context?) : Array<String> {
                val titles : Array<String> = Array(PortfolioType.values().size) { "" }
                enumValues<PortfolioType>().forEach { titles[it.ordinal] = context!!.getString(it.titleResId) }
                return titles
            }
        }
    }

    companion object {
        fun newInstance(type: Int): PortfolioSubFragment {
            val fragment = PortfolioSubFragment()
            val args = Bundle()
            args.putInt(KEY_TYPE, type)
            fragment.arguments = args
            return fragment
        }
    }

    lateinit var type: PortfolioType

    override fun initializeDaggerComponent() {
        //TODO impl
    }

    override fun layoutResId(): Int {
        return R.layout.sub_fragment_plants
    }

    override fun initViews() {
        type = PortfolioType.values()[arguments!!.getInt(KEY_TYPE)]

        test.text = getString(type.titleResId)
    }
}