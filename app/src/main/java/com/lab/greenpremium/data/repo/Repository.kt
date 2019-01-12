package com.lab.greenpremium.data.repo

import android.annotation.SuppressLint
import com.google.gson.JsonParser
import com.lab.greenpremium.REQUEST_REFRESH_TIME_MS
import com.lab.greenpremium.data.CartModel
import com.lab.greenpremium.data.UserModel
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

        if (UserModel.contactsResponse != null) {
            if (System.currentTimeMillis() - UserModel.contactsResponse!!.time < REQUEST_REFRESH_TIME_MS) {
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

        if (UserModel.objectInfoResponse != null) {
            if (System.currentTimeMillis() - UserModel.objectInfoResponse!!.time < REQUEST_REFRESH_TIME_MS) {
                listener.onSuccess()
                return
            }
        }

        if (UserModel.authResponse == null) {
            listener.onError(ApiError(401, "Not authorized"))
            return
        }

        apiMethods.getObjectInfo(UserModel.authResponse!!.token)
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
    fun updateEvents(forced: Boolean, listener: CallbackListener) {

        if (!forced && UserModel.eventsResponse != null) {
            if (System.currentTimeMillis() - UserModel.eventsResponse!!.time < REQUEST_REFRESH_TIME_MS) {
                listener.onSuccess()
                return
            }
        }

        if (UserModel.authResponse == null) {
            listener.onError(ApiError(401, "Not authorized"))
            return
        }

        apiMethods.getEvents(UserModel.authResponse!!.token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { listener.doBefore() }
                .doFinally { listener.doAfter() }
                .subscribe(
                        { response -> handleResponse(BaseResponse(response.status, response.title, EventsResponse(response.data)), listener) },
                        { error -> handleError(error, listener) }
                )
    }

    @SuppressLint("CheckResult")
    fun calculateServiceCost(request: CalcServiceRequest, listener: CallbackListener) {

        if (UserModel.authResponse == null) {
            listener.onError(ApiError(401, "Not authorized"))
            return
        }

        apiMethods.calculateService(UserModel.authResponse!!.token, request)
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
    fun getOrderList(request: OrderRequest, listener: CallbackListener) {

        if (UserModel.authResponse == null) {
            listener.onError(ApiError(401, "Not authorized"))
            return
        }

        apiMethods.getOrderList(UserModel.authResponse!!.token, request)
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
    fun updateMeetingsList(listener: CallbackListener) {

        if (UserModel.meetingsListResponse != null) {
            if (System.currentTimeMillis() - UserModel.meetingsListResponse!!.time < REQUEST_REFRESH_TIME_MS) {
                listener.onSuccess()
                return
            }
        }

        if (UserModel.authResponse == null) {
            listener.onError(ApiError(401, "Not authorized"))
            return
        }

        apiMethods.getMeetingsList(UserModel.authResponse!!.token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { listener.doBefore() }
                .doFinally { listener.doAfter() }
                .subscribe(
                        { response -> handleResponse(BaseResponse(response.status, response.title, MeetingsListResponse(response.data)), listener) },
                        { error -> handleError(error, listener) }
                )
    }

    @SuppressLint("CheckResult")
    fun addMeeting(manager_id: String, date: String, listener: CallbackListener) {
        if (UserModel.authResponse == null) {
            listener.onError(ApiError(401, "Not authorized"))
            return
        }

        apiMethods.addMeeting(UserModel.authResponse!!.token, MeetingAddRequest(manager_id, date))
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

        if (UserModel.catalog != null) {
            if (System.currentTimeMillis() - UserModel.catalog!!.time < REQUEST_REFRESH_TIME_MS) {
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
                        { response -> handleResponse(BaseResponse(response.status, response.title, CatalogSectionsResponse(response.data)), listener) },
                        { error -> handleError(error, listener) }
                )
    }

    @SuppressLint("CheckResult")
    fun getSectionProductsList(section_id: Int, listener: CallbackListener) {

        if (UserModel.authResponse == null) {
            listener.onError(ApiError(401, "Not authorized"))
            return
        }

        apiMethods.getSectionProductsList(UserModel.authResponse!!.token, SectionProductListRequest(section_id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { listener.doBefore() }
                .doFinally { listener.doAfter() }
                .subscribe(
                        { response ->
                            handleResponse(
                                    BaseResponse(response.status, response.title, SectionProductsResponse(response.data)),
                                    listener, section_id)
                        },

                        { error -> handleError(error, listener) }
                )
    }

    @SuppressLint("CheckResult")
    fun getProductDetail(section_id: Int, product_id: Int, listener: CallbackListener) {

        if (UserModel.authResponse == null) {
            listener.onError(ApiError(401, "Not authorized"))
            return
        }

        apiMethods.getProductDetail(UserModel.authResponse!!.token, ProductRequest(product_id))
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
    fun getCart(listener: CallbackListener) {

        if (UserModel.authResponse == null) {
            listener.onError(ApiError(401, "Not authorized"))
            return
        }

        apiMethods.getCart(UserModel.authResponse!!.token)
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
    fun addToCart(product_id: Int, quantity: Int, listener: CallbackListener) {

        if (UserModel.authResponse == null) {
            listener.onError(ApiError(401, "Not authorized"))
            return
        }

        apiMethods.addToCart(UserModel.authResponse!!.token, AddToCartRequest(product_id, quantity))
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
    fun makeOrder(listener: CallbackListener) {

        if (UserModel.authResponse == null) {
            listener.onError(ApiError(401, "Not authorized"))
            return
        }

        apiMethods.makeOrder(UserModel.authResponse!!.token)
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
                        { response -> handleResponse(BaseResponse(response.status, response.title, PortfolioResponse(response.data)), listener) },
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

                AuthResponse::class -> {
                    val authData = response.data as AuthResponse
                    UserModel.authResponse = authData
                    preferences.setToken(authData.token)
                }

                ContactsResponse::class -> {
                    UserModel.contactsResponse = response.data as ContactsResponse
                }

                ObjectInfoResponse::class -> {
                    UserModel.objectInfoResponse = response.data as ObjectInfoResponse
                }

                EventsResponse::class -> {
                    UserModel.eventsResponse = response.data as EventsResponse
                }

                CalcServiceResponse::class -> {
                    listener.onSuccess(response.data as CalcServiceResponse)
                }

                OrderResponse::class -> {
                    UserModel.orderResponse = response.data as OrderResponse
                }

                MeetingsListResponse::class -> {
                    UserModel.meetingsListResponse = response.data as MeetingsListResponse
                }

                MeetingsAddResponse::class -> {
                    //ignore
                }

                CatalogSectionsResponse::class -> {
                    UserModel.catalog = response.data as CatalogSectionsResponse
                }

                SectionProductsResponse::class -> {
                    val catalogSectionsData = UserModel.catalog
                    try {
                        catalogSectionsData!!.sections?.forEach { section ->
                            if (section.id == parameters[0]) {
                                section.products = (response.data as SectionProductsResponse).products
                            }
                        }
                    } catch (e: Exception) {
                        LogUtil.e(e.toString())
                    }
                }

                Product::class -> {
                    listener.onSuccess(response.data as Product)
                }

                CartResponse::class -> {
                    CartModel.products = (response.data as CartResponse).products
                }

                MakeOrderResponse::class -> {
                    listener.onSuccess(response.data as MakeOrderResponse)
                }

                MapObjectsResponse::class -> {
                    UserModel.mapObjectsResponse = response.data as MapObjectsResponse
                }

                PortfolioResponse::class -> {
                    UserModel.portfolio = response.data as PortfolioResponse
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