package com.lab.greenpremium.ui.screens.calculator

import android.annotation.SuppressLint
import com.lab.greenpremium.R
import com.lab.greenpremium.data.repo.Repository
import com.lab.greenpremium.data.entity.CalcServiceRequest
import com.lab.greenpremium.data.entity.CalcServiceResponse
import com.lab.greenpremium.data.network.DefaultCallbackListener
import io.reactivex.Observable
import java.io.Serializable
import javax.inject.Inject

class CalcPresenter @Inject constructor(val view: CalcContract.View) : CalcContract.Presenter {

    @Inject
    internal lateinit var repository: Repository

    private val model = CalcServiceRequest()

    @SuppressLint("CheckResult")
    override fun initializeDataInput(plantsCount1: Observable<String>, potsCount1: Observable<String>,
                                     plantsCount2: Observable<String>, potsCount2: Observable<String>,
                                     plantsCount3: Observable<String>, potsCount3: Observable<String>,
                                     plantsCount4: Observable<String>, potsCount4: Observable<String>,
                                     plantsCount5: Observable<String>, potsCount5: Observable<String>) {

        plantsCount1.subscribe({ s -> model.plants_count_s1 = s.toInt() }, { model.plants_count_s1 = 0 })
        plantsCount2.subscribe({ s -> model.plants_count_s2 = s.toInt() }, { model.plants_count_s2 = 0 })
        plantsCount3.subscribe({ s -> model.plants_count_s3 = s.toInt() }, { model.plants_count_s3 = 0 })
        plantsCount4.subscribe({ s -> model.plants_count_s4 = s.toInt() }, { model.plants_count_s4 = 0 })
        plantsCount5.subscribe({ s -> model.plants_count_s5 = s.toInt() }, { model.plants_count_s5 = 0 })

        potsCount1.subscribe({ s -> model.pots_count_s1 = s.toInt() }, { model.pots_count_s1 = 0 })
        potsCount2.subscribe({ s -> model.pots_count_s2 = s.toInt() }, { model.pots_count_s2 = 0 })
        potsCount3.subscribe({ s -> model.pots_count_s3 = s.toInt() }, { model.pots_count_s3 = 0 })
        potsCount4.subscribe({ s -> model.pots_count_s4 = s.toInt() }, { model.pots_count_s4 = 0 })
        potsCount5.subscribe({ s -> model.pots_count_s5 = s.toInt() }, { model.pots_count_s5 = 0 })
    }

    override fun onClickCalcRequest() {
        if (model.isChanged()) {
            repository.calculateServiceCost(model, object : DefaultCallbackListener(view) {
                override fun onSuccess(item: Serializable?) {
                    item?.let { response ->
                        val message = (response as CalcServiceResponse).message
                        this@CalcPresenter.view.onCalculateSuccess(message)
                    }
                }
            })
        } else {
            view.showSnackbar(null, R.string.calc_params_is_not_set_error)
        }
    }
}