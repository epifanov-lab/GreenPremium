package com.lab.greenpremium.ui.screens.message

import com.lab.greenpremium.ui.screens.base.BaseContract
import io.reactivex.Observable


interface MessageContract {
    interface View : BaseContract.BaseView {
        fun initViewByType(type: MessageScreenType)
        fun onSentSuccess(messageResId: Int)
        fun setSendButtonEnabled(isEnabled: Boolean)
        fun showPhotoPickerDialog()
    }

    interface Presenter {
        fun onViewCreated(type: MessageScreenType)
        fun initializeThemeInput(theme: Observable<String>)
        fun initializeMessageInput(message: Observable<String>)
        fun onRatingChanged(rating: Float)
        fun onClickAddPhoto()
        fun onClickSend()
    }
}