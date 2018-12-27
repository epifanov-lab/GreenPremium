package com.lab.greenpremium.ui.screen.calculator

import com.lab.greenpremium.data.Repository
import com.lab.greenpremium.data.entity.CalcServiceRequest
import com.lab.greenpremium.utills.LogUtil
import io.reactivex.Observable
import javax.inject.Inject

class CalcPresenter @Inject constructor(val view: CalcContract.View) : CalcContract.Presenter {

    @Inject
    internal lateinit var repository: Repository

    private val model = CalcServiceRequest()

    override fun initializeDataInput(plantsCount1: Observable<String>, potsCount1: Observable<String>,
                                     plantsCount2: Observable<String>, potsCount2: Observable<String>,
                                     plantsCount3: Observable<String>, potsCount3: Observable<String>,
                                     plantsCount4: Observable<String>, potsCount4: Observable<String>,
                                     plantsCount5: Observable<String>, potsCount5: Observable<String>) {

        plantsCount1.subscribe{ s -> model.plants_count_s1 = s.toInt()}
        plantsCount2.subscribe{ s -> model.plants_count_s2 = s.toInt()}
        plantsCount3.subscribe{ s -> model.plants_count_s3 = s.toInt()}
        plantsCount4.subscribe{ s -> model.plants_count_s4 = s.toInt()}
        plantsCount5.subscribe{ s -> model.plants_count_s5 = s.toInt()}

        potsCount1.subscribe{ s -> model.plants_count_s1 = s.toInt()}
        potsCount2.subscribe{ s -> model.plants_count_s2 = s.toInt()}
        potsCount3.subscribe{ s -> model.plants_count_s3 = s.toInt()}
        potsCount4.subscribe{ s -> model.plants_count_s4 = s.toInt()}
        potsCount5.subscribe{ s -> model.plants_count_s5 = s.toInt()}
    }

    override fun onClickCalcRequest() {
        LogUtil.e("MODEL: $model")
    }
}