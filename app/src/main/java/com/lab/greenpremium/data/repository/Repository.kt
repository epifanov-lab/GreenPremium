package com.lab.greenpremium.data.repository

import android.annotation.SuppressLint
import com.google.gson.JsonParser
import com.lab.greenpremium.REQUEST_REFRESH_TIME_MS
import com.lab.greenpremium.data.CartModel
import com.lab.greenpremium.data.EventsPaginationStateChanging
import com.lab.greenpremium.data.ProductPaginationStateChanging
import com.lab.greenpremium.data.UserModel
import com.lab.greenpremium.data.entity.*
import com.lab.greenpremium.data.local.PreferencesManager
import com.lab.greenpremium.data.network.ApiError
import com.lab.greenpremium.data.network.ApiMethods
import com.lab.greenpremium.data.network.CallbackListener
import com.lab.greenpremium.utills.LogUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody
import org.greenrobot.eventbus.EventBus
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

        if (checkAuthorization(listener)) return

        if (UserModel.contactsResponse != null) {
            if (System.currentTimeMillis() - UserModel.contactsResponse!!.time < REQUEST_REFRESH_TIME_MS) {
                listener.onSuccess()
                return
            }
        }

        apiMethods.getContacts(preferences.getToken())
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

        if (checkAuthorization(listener)) return

        apiMethods.getObjectInfo(preferences.getToken())
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
    fun updateEvents(forced: Boolean = false, listener: CallbackListener) {

        if (!forced && UserModel.eventsResponse != null) {
            if (System.currentTimeMillis() - UserModel.eventsResponse!!.time < REQUEST_REFRESH_TIME_MS) {
                listener.onSuccess()
                return
            }
        }

        if (checkAuthorization(listener)) return

        apiMethods.getEvents(preferences.getToken())
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
    fun getEventsNextPage(page: Int, listener: CallbackListener) {

        if (checkAuthorization(listener)) return

        apiMethods.getEventsNextPage(preferences.getToken(), page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { listener.doBefore() }
                .doFinally { listener.doAfter() }
                .subscribe(
                        { response ->
                            run {
                                val data = EventsResponse(response.data)
                                data.page = page
                                handleResponse(BaseResponse(response.status, response.title, data), listener)
                            }
                        },
                        { error -> handleError(error, listener) }
                )
    }

    @SuppressLint("CheckResult")
    fun calculateServiceCost(request: CalcServiceRequest, listener: CallbackListener) {

        if (checkAuthorization(listener)) return

        apiMethods.calculateService(preferences.getToken(), request)
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

        if (checkAuthorization(listener)) return

        apiMethods.getOrderList(preferences.getToken(), request)
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

        if (checkAuthorization(listener)) return

        apiMethods.getMeetingsList(preferences.getToken())
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
        if (checkAuthorization(listener)) return

        apiMethods.addMeeting(preferences.getToken(), MeetingAddRequest(manager_id, date))
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

        if (CartModel.catalog != null) {
            listener.onSuccess()
            return
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

        CartModel.catalog?.sections?.forEach {
            if (it.id == section_id) {
                if (it.products != null && it.products!!.isNotEmpty()) {
                    listener.onSuccess()
                    return
                }
            }
        }

        if (checkAuthorization(listener)) return

        apiMethods.getSectionProductsList(preferences.getToken(), SectionProductListRequest(section_id))
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
    fun getSectionProductsListNextPage(section_id: Int, page: Int, listener: CallbackListener) {

        if (checkAuthorization(listener)) return

        apiMethods.getSectionProductsListNextPage(preferences.getToken(), SectionProductListRequest(section_id), page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { listener.doBefore() }
                .doFinally { listener.doAfter() }
                .subscribe(
                        { response ->
                            val data = SectionProductsResponse(response.data)
                            data.page = page
                            handleResponse(
                                    BaseResponse(response.status, response.title, data),
                                    listener, section_id)
                        },

                        { error -> handleError(error, listener) }
                )
    }

    @SuppressLint("CheckResult")
    fun getProductDetail(section_id: Int, product_id: Int, listener: CallbackListener) {

        if (checkAuthorization(listener)) return

        apiMethods.getProductDetail(preferences.getToken(), ProductRequest(product_id))
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

        if (checkAuthorization(listener)) return

        apiMethods.getCart(preferences.getToken())
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

        if (checkAuthorization(listener)) return

        apiMethods.addToCart(preferences.getToken(), AddToCartRequest(product_id, quantity))
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

        if (checkAuthorization(listener)) return

        apiMethods.makeOrder(preferences.getToken())
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
    fun editFavorites(product: Product, listener: CallbackListener) {

        if (checkAuthorization(listener)) return

        val request = EditFavoritesRequest(product.isFavorite, product.getSelectedOffer().product_id)

        apiMethods.editFavorites(preferences.getToken(), request)
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
    fun getFavorites(listener: CallbackListener) {

        if (checkAuthorization(listener)) return

        apiMethods.getFavorites(preferences.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { listener.doBefore() }
                .doFinally { listener.doAfter() }
                .subscribe(
                        { response -> handleResponse(BaseResponse(response.status, response.title, GetFavoritesResponse(response.data)), listener) },
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

    @SuppressLint("CheckResult")
    fun addProjects(message: String, photos: List<MultipartBody.Part>, listener: CallbackListener) {

        if (checkAuthorization(listener)) return

        apiMethods.addProjects(preferences.getToken(), AddProjectRequest(message, photos))
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
    fun addMessageRequest(theme: String, message: String, photos: List<MultipartBody.Part>, listener: CallbackListener) {

        if (checkAuthorization(listener)) return

        apiMethods.addMessages(preferences.getToken(), AddMessageRequest(theme, message, photos))
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
    fun addClaim(message: String, photos: List<MultipartBody.Part>, listener: CallbackListener) {

        if (checkAuthorization(listener)) return

        apiMethods.addClaims(preferences.getToken(), AddClaimRequest(message, photos))
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
    fun addRating(rating: Int, message: String, listener: CallbackListener) {

        if (checkAuthorization(listener)) return

        apiMethods.addRatings(preferences.getToken(), AddRatingRequest(rating, message))
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
                    preferences.setDemoMode(authData.is_demo)
                    preferences.setToken(authData.token)
                }

                ContactsResponse::class -> {
                    UserModel.contactsResponse = response.data as ContactsResponse
                }

                ObjectInfoResponse::class -> {
                    val objectInfoResponse = response.data as ObjectInfoResponse
                    objectInfoResponse.updateBiologists()
                    UserModel.objectInfoResponse = objectInfoResponse
                }

                EventsResponse::class -> {
                    val eventsResponse = response.data as EventsResponse
                    if (eventsResponse.page == 1) {
                        UserModel.eventsResponse = eventsResponse
                        EventBus.getDefault().post(EventsPaginationStateChanging(true))
                    } else {
                        val events = eventsResponse.events
                        if (events.isNotEmpty()) UserModel.eventsResponse?.events?.addAll(events)
                        else EventBus.getDefault().post(EventsPaginationStateChanging(false))
                    }
                }

                CalcServiceResponse::class -> {
                    listener.onSuccess(response.data as CalcServiceResponse)
                }

                OrderResponse::class -> {
                    UserModel.orderResponse = response.data as OrderResponse
                }

                EditFavoriteResponse::class -> {
                    //ignore
                }

                GetFavoritesResponse::class -> {
                    CartModel.favorites = (response.data as GetFavoritesResponse).products
                }

                MeetingsListResponse::class -> {
                    UserModel.meetingsListResponse = response.data as MeetingsListResponse
                }

                MeetingsAddResponse::class -> {
                    //ignore
                }

                CatalogSectionsResponse::class -> {
                    CartModel.catalog = response.data as CatalogSectionsResponse
                }

                SectionProductsResponse::class -> {
                    val catalogSectionsData = CartModel.catalog
                    try {
                        catalogSectionsData!!.sections?.forEach { section ->
                            val sectionProductsResponse = response.data as SectionProductsResponse
                            if (section.id == parameters[0]) {
                                val products = sectionProductsResponse.products
                                if (sectionProductsResponse.page == 1) {
                                    section.products = products
                                    EventBus.getDefault().post(ProductPaginationStateChanging(true))
                                } else {
                                    if (products.isNotEmpty()) section.products?.addAll(products)
                                    else EventBus.getDefault().post(ProductPaginationStateChanging(false))
                                }
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
                    CartModel.cart = response.data as CartResponse
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

    private fun checkAuthorization(listener: CallbackListener): Boolean {
        if (!isAuthorized()) {
            listener.onError(ApiError(401, "Not authorized"))
            return true
        }

        return false
    }

    fun isAuthorized() : Boolean {
        return !preferences.getToken().isNullOrEmpty()
    }

    fun isInDemoMode() : Boolean {
        return preferences.getIsDemoMode()
    }

    fun logout() {
        UserModel.clearModel()
        CartModel.clearModel()
        preferences.clear()
    }

}