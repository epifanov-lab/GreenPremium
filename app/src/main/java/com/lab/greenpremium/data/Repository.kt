package com.lab.greenpremium.data

import android.annotation.SuppressLint
import com.google.gson.JsonParser
import com.lab.greenpremium.REQUEST_REFRESH_TIME_MS
import com.lab.greenpremium.data.entity.*
import com.lab.greenpremium.data.local.PreferencesManager
import com.lab.greenpremium.data.network.ApiError
import com.lab.greenpremium.data.network.ApiMethods
import com.lab.greenpremium.data.network.CallbackListener
import com.lab.greenpremium.utills.LogUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import javax.inject.Inject


class Repository @Inject constructor(private val apiMethods: ApiMethods,
                                     private val preferences: PreferencesManager) {

    @SuppressLint("CheckResult")
    fun auth(login: String, password: String, listener: CallbackListener) {
        apiMethods.auth(AuthRequest(login, password))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { listener.doBefore() }
                .doFinally { listener.doAfter() }
                .subscribe(
                        { response -> handleResponse(response, listener) },
                        { error -> handleError(error, listener) }
                )
    }

    @SuppressLint("CheckResult")
    fun updateContacts(listener: CallbackListener) {

        if (UserModel.contacts != null) {
            if (System.currentTimeMillis() - UserModel.contacts!!.time < REQUEST_REFRESH_TIME_MS) {
                listener.onSuccess()
                return
            }
        }

        apiMethods.getContacts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { listener.doBefore() }
                .doFinally { listener.doAfter() }
                .subscribe(
                        { response -> handleResponse(response, listener) },
                        { error -> handleError(error, listener) }
                )
    }

    @SuppressLint("CheckResult")
    fun updateObjectsInfo(listener: CallbackListener) {

        if (UserModel.objectInfo != null) {
            if (System.currentTimeMillis() - UserModel.objectInfo!!.time < REQUEST_REFRESH_TIME_MS) {
                listener.onSuccess()
                return
            }
        }

        if (UserModel.authData == null) {
            listener.onError(ApiError(401, "Not authorized"))
            return
        }

        apiMethods.getObjectInfo(UserModel.authData!!.token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { listener.doBefore() }
                .doFinally { listener.doAfter() }
                .subscribe(
                        { response -> handleResponse(response, listener) },
                        { error -> handleError(error, listener) }
                )
    }

    @SuppressLint("CheckResult")
    fun updateEvents(listener: CallbackListener) {

        if (UserModel.eventsData != null) {
            if (System.currentTimeMillis() - UserModel.eventsData!!.time < REQUEST_REFRESH_TIME_MS) {
                listener.onSuccess()
                return
            }
        }

        if (UserModel.authData == null) {
            listener.onError(ApiError(401, "Not authorized"))
            return
        }

        apiMethods.getEvents(UserModel.authData!!.token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { listener.doBefore() }
                .doFinally { listener.doAfter() }
                .subscribe(
                        { response -> handleResponse(BaseResponse(response.status, response.title, EventsData(response.data)), listener) },
                        { error -> handleError(error, listener) }
                )
    }

    @SuppressLint("CheckResult")
    fun updateMeetingsList(listener: CallbackListener) {

        if (UserModel.meetingsListData != null) {
            if (System.currentTimeMillis() - UserModel.meetingsListData!!.time < REQUEST_REFRESH_TIME_MS) {
                listener.onSuccess()
                return
            }
        }

        if (UserModel.authData == null) {
            listener.onError(ApiError(401, "Not authorized"))
            return
        }

        apiMethods.getMeetingsList(UserModel.authData!!.token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { listener.doBefore() }
                .doFinally { listener.doAfter() }
                .subscribe(
                        { response -> handleResponse(BaseResponse(response.status, response.title, MeetingsListData(response.data)), listener) },
                        { error -> handleError(error, listener) }
                )
    }

    @SuppressLint("CheckResult")
    fun addMeeting(manager_id: String, date: String, listener: CallbackListener) {
        if (UserModel.authData == null) {
            listener.onError(ApiError(401, "Not authorized"))
            return
        }

        apiMethods.addMeeting(UserModel.authData!!.token, manager_id, date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { listener.doBefore() }
                .doFinally { listener.doAfter() }
                .subscribe(
                        { response -> handleResponse(response, listener) },
                        { error -> handleError(error, listener) }
                )
    }

    @SuppressLint("CheckResult")
    fun getCatalogSections(listener: CallbackListener) {

        if (UserModel.catalogSectionsData != null) {
            if (System.currentTimeMillis() - UserModel.catalogSectionsData!!.time < REQUEST_REFRESH_TIME_MS) {
                listener.onSuccess()
                return
            }
        }

        apiMethods.getCatalogSections()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { listener.doBefore() }
                .doFinally { listener.doAfter() }
                .subscribe(
                        { response -> handleResponse(BaseResponse(response.status, response.title, CatalogSectionsData(response.data)), listener) },
                        { error -> handleError(error, listener) }
                )
    }

    @SuppressLint("CheckResult")
    fun getSectionProductsList(section_id: Int, listener: CallbackListener) {

        if (UserModel.authData == null) {
            listener.onError(ApiError(401, "Not authorized"))
            return
        }

        apiMethods.getSectionProductsList(UserModel.authData!!.token, section_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { listener.doBefore() }
                .doFinally { listener.doAfter() }
                .subscribe(
                        { response ->
                            handleResponse(
                                    BaseResponse(response.status, response.title, SectionProductsData(response.data)),
                                    listener, section_id)
                        },

                        { error -> handleError(error, listener) }
                )
    }

    @SuppressLint("CheckResult")
    fun getProductDetail(section_id: Int, product_id: Int, listener: CallbackListener) {

        if (UserModel.authData == null) {
            listener.onError(ApiError(401, "Not authorized"))
            return
        }

        apiMethods.getProductDetail(UserModel.authData!!.token, product_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { listener.doBefore() }
                .doFinally { listener.doAfter() }
                .subscribe(
                        { response -> handleResponse(response, listener, section_id, product_id) },
                        { error -> handleError(error, listener) }
                )
    }

    @SuppressLint("CheckResult")
    fun updatePortfolio(listener: CallbackListener) {

        if (UserModel.portfolio != null) {
            if (System.currentTimeMillis() - UserModel.portfolio!!.time < REQUEST_REFRESH_TIME_MS) {
                listener.onSuccess()
                return
            }
        }

        apiMethods.getPortfolio()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { listener.doBefore() }
                .doFinally { listener.doAfter() }
                .subscribe(
                        { response -> handleResponse(BaseResponse(response.status, response.title, PortfolioData(response.data)), listener) },
                        { error -> handleError(error, listener) }
                )
    }

    @SuppressLint("CheckResult")
    fun updateMapObjects(listener: CallbackListener) {
        apiMethods.getMapObjects()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { listener.doBefore() }
                .doFinally { listener.doAfter() }
                .subscribe(
                        { response -> handleResponse(response, listener) },
                        { error -> handleError(error, listener) }
                )
    }

    private inline fun <reified DATA> handleResponse(response: BaseResponse<DATA>,
                                                     listener: CallbackListener,
                                                     vararg parameters: Int) {

        LogUtil.i("HANDLE_HTTP_RESPONSE: ${response.data}")

        if (response.status == 200) {
            when (DATA::class) {

                AuthData::class -> {
                    val authData = response.data as AuthData
                    UserModel.authData = authData
                    preferences.setToken(authData.token)
                }

                ContactsData::class -> {
                    UserModel.contacts = response.data as ContactsData
                }

                ObjectInfo::class -> {
                    UserModel.objectInfo = response.data as ObjectInfo
                }

                EventsData::class -> {
                    UserModel.eventsData = response.data as EventsData
                }

                MeetingsListData::class -> {
                    UserModel.meetingsListData = response.data as MeetingsListData
                }

                MeetingsAddResponse::class -> {
                    // TODO ???
                }

                CatalogSectionsData::class -> {
                    UserModel.catalogSectionsData = response.data as CatalogSectionsData
                }

                SectionProductsData::class -> {
                    val catalogSectionsData = UserModel.catalogSectionsData
                    try {
                        catalogSectionsData!!.sections?.forEach { section ->
                            if (section.id == parameters[0]) {
                                section.products = (response.data as SectionProductsData).products
                            }
                        }
                    } catch (e: Exception) {
                        LogUtil.e(e.toString())
                    }
                }

                Product::class -> {
                    listener.onSuccess(response.data as Product)
                }

                MapObjectsData::class -> {
                    UserModel.mapObjectsData = response.data as MapObjectsData
                }

                PortfolioData::class -> {
                    UserModel.portfolio = response.data as PortfolioData
                }

            }

            listener.onSuccess()

        } else {
            listener.onError(ApiError(response.status, response.title))
        }

    }

    private fun handleError(throwable: Throwable, listener: CallbackListener) {
        LogUtil.i("HANDLE_HTTP_ERROR: $throwable")

        fun getResponseErrorFromJson(json: String): ApiError {
            return ApiError(
                    JsonParser().parse(json).asJsonObject["status"].asInt,
                    JsonParser().parse(json).asJsonObject["title"].asString
            )
        }

        if (throwable is HttpException) {
            val body = throwable.response().errorBody()?.string()

            if (body != null) listener.onError(getResponseErrorFromJson(body))
            else listener.onError(throwable)

        } else {
            listener.onError(throwable)
        }
    }
}