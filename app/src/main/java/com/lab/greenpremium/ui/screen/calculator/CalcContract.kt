package com.lab.greenpremium.ui.screen.calculator

import com.lab.greenpremium.ui.screen.base.BaseContract
import io.reactivex.Observable


interface CalcContract {
    interface View : BaseContract.BaseView

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