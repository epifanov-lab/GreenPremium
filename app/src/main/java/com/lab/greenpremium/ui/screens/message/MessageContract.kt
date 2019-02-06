package com.lab.greenpremium.ui.screens.message

import android.content.Context
import com.lab.greenpremium.ui.screens.base.BaseContract
import io.reactivex.Observable


interface MessageContract {
    interface View : BaseContract.BaseView {
        fun initViewByType(type: MessageScreenType)
        fun onSentSuccess(messageResId: Int)
        fun setSendButtonEnabled(isEnabled: Boolean)
        fun initializeRecyclerPhotos()
        fun showPhotoPickerDialog()

        fun getPhotos() : MutableList<RecyclerPhotosAdapter.PhotoUriWrapper>
    }

    interface Presenter {
        fun onViewCreated(type: MessageScreenType)
        fun initializeThemeInput(theme: Observable<String>)
        fun initializeMessageInput(message: Observable<String>)
        fun onRatingChanged(rating: Float)
        fun onClickAddPhoto()
        fun onClickSend(context: Context)
    }
}