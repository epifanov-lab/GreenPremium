package com.lab.greenpremium.ui.screens.calculator

import com.lab.greenpremium.ui.screens.base.BaseContract
import io.reactivex.Observable


interface CalcContract {
    interface View : BaseContract.BaseView {
        fun onCalculateSuccess(message: String)
    }

    interface Presenter {
        fun initializeDataInput(
                plantsCount1: Observable<String>, potsCount1: Observable<String>,
                plantsCount2: Observable<String>, potsCount2: Observable<String>,
                plantsCount3: Observable<String>, potsCount3: Observable<String>,
                plantsCount4: Observable<String>, potsCount4: Observable<String>,
                plantsCount5: Observable<String>, potsCount5: Observable<String>
        )

        fun onClickCalcRequest()
    }
}