package com.lab.greenpremium.ui.screen.message

import com.lab.greenpremium.R
import java.io.Serializable

enum class MessageScreenType(var titleResId: Int) : Serializable {

    NEW_PROJECT(R.string.fab_project),
    LETTER(R.string.fab_letter),
    PRAISE(R.string.fab_praise),
    COMPLAIN(R.string.fab_complain);

}