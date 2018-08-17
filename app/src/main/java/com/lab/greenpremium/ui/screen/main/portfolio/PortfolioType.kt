package com.lab.greenpremium.ui.screen.main.portfolio

import android.content.Context
import com.lab.greenpremium.R
import java.io.Serializable

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