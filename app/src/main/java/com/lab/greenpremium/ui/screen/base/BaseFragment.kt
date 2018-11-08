package com.lab.greenpremium.ui.screen.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lab.greenpremium.data.entity.raw.Plant
import com.lab.greenpremium.data.repository.UserModel
import com.lab.greenpremium.ui.components.adapters.PlantRecyclerAdapter


abstract class BaseFragment : Fragment(), PlantRecyclerAdapter.OnPlantSelectedListener, BaseContract.BaseView {

    lateinit var TAG: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        TAG = this::class.java.simpleName
        initializeDaggerComponent()
        return inflater.inflate(layoutResId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    protected abstract fun initializeDaggerComponent()

    protected abstract fun layoutResId(): Int

    protected abstract fun initViews()

    override fun onPlantSelected(plant: Plant) {
        (activity as BaseActivity).goToPlantDetailActivity(UserModel.plants.indexOf(plant))
    }

    override fun showError(text: String?, textResId: Int?) {
        //todo implement
    }

    override fun showSnackbar(text: String?, textResId: Int?) {
        //todo implement
    }

    override fun showDialogMessage(text: String?, textResId: Int?) {
        //todo implement
    }

    override fun showLoadingDialog(show: Boolean) {
        //todo implement
    }
}
