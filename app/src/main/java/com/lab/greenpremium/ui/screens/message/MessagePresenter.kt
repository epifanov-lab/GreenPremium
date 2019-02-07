package com.lab.greenpremium.ui.screens.message

import android.annotation.SuppressLint
import android.content.Context
import com.lab.greenpremium.data.network.DefaultCallbackListener
import com.lab.greenpremium.data.repository.Repository
import com.lab.greenpremium.utills.LogUtil
import com.lab.greenpremium.utills.getMultipartEntityFromPhotoUri
import io.reactivex.Observable
import okhttp3.MultipartBody
import javax.inject.Inject

class MessagePresenter @Inject constructor(val view: MessageContract.View) : MessageContract.Presenter {

    @Inject
    internal lateinit var repository: Repository

    private lateinit var type: MessageScreenType

    private var theme: String? = null
    private var message: String? = null
    private var rating: Int = 5

    override fun onViewCreated(type: MessageScreenType) {
        this.type = type
        view.initViewByType(type)
        view.setSendButtonEnabled(!repository.isInDemoMode())
    }

    @SuppressLint("CheckResult")
    override fun initializeThemeInput(theme: Observable<String>) {
        theme.subscribe({s -> this.theme = s}, {this.theme = null})
    }

    @SuppressLint("CheckResult")
    override fun initializeMessageInput(message: Observable<String>) {
        message.subscribe({s -> this.message = s}, {this.message = null})
    }

    override fun onRatingChanged(rating: Float) {
        this.rating = rating.toInt()
    }

    override fun onClickAddPhoto() {
        view.showPhotoPickerDialog()
    }

    override fun onClickSend(context: Context) {

        val listener = object : DefaultCallbackListener(view) {
            override fun onSuccess() {
                this@MessagePresenter.view.onSentSuccess(type.successMessageResId)
            }
        }

        when(type) {
            MessageScreenType.NEW_PROJECT -> repository.addProjects(message!!, getPreparedPhotos(context), listener)
            MessageScreenType.LETTER -> repository.addMessageRequest(theme!!, message!!, getPreparedPhotos(context), listener)
            MessageScreenType.COMPLAIN -> repository.addClaim(message!!, getPreparedPhotos(context), listener)
            MessageScreenType.RATING -> repository.addRating(rating, message!!, listener)
        }
    }

    private fun getPreparedPhotos(context: Context): List<MultipartBody.Part> {
        var result = listOf<MultipartBody.Part>()
        val photos = view.getPhotos()
        if (!photos.isNullOrEmpty()) {
            result = photos.subList(0, photos.lastIndex)
                    .mapIndexed { index, wrapper -> getMultipartEntityFromPhotoUri(context, wrapper.uri, index) }

            LogUtil.i("MP\n ${photos[0]}")

        }
        return result
    }

}