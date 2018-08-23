package com.lab.greenpremium.ui.screen.message

import com.lab.greenpremium.R
import java.io.Serializable

enum class MessageScreenType(var titleResId: Int,
                             val hasRatingBar: Boolean,
                             val hasSubjectInput: Boolean,
                             val hasMessageInput: Boolean,
                             val hasPhotoAdding: Boolean,
                             val hasDocsAdding: Boolean) : Serializable {

    NEW_PROJECT(R.string.fab_project, false, false, true, true, true),
    LETTER(R.string.fab_letter, false, true, true, true, true),
    PRAISE(R.string.fab_praise, true, false, true, true, false),
    COMPLAIN(R.string.fab_complain, false, false, true, true, false);

}