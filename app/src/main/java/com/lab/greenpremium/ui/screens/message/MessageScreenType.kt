package com.lab.greenpremium.ui.screens.message

import com.lab.greenpremium.R
import java.io.Serializable

enum class MessageScreenType(var titleResId: Int,
                             val hasRatingBar: Boolean,
                             val hasSubjectInput: Boolean,
                             val hasMessageInput: Boolean,
                             val hasPhotoAdding: Boolean,
                             val hasDocsAdding: Boolean,
                             val successMessageResId: Int) : Serializable {

    NEW_PROJECT(R.string.fab_project, false, false, true, true, true, R.string.messages_screen_succes_add_project),
    LETTER(R.string.fab_letter, false, true, true, true, true, R.string.messages_screen_succes_add_letter),
    RATING(R.string.fab_praise, true, false, true, true, false, R.string.messages_screen_succes_add_rating),
    COMPLAIN(R.string.fab_complain, false, false, true, true, false, R.string.messages_screen_succes_add_complain);

}