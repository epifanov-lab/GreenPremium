package com.lab.greenpremium.ui.screens.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lab.greenpremium.ui.components.Listener


abstract class BaseFragment : Fragment(), BaseContract.BaseView {

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

    override fun showError(throwable: Throwable) {
        (activity as BaseActivity).showError(throwable)
    }

    override fun showSnackbar(text: String?, textResId: Int?) {
        (activity as BaseActivity).showSnackbar(text, textResId)
    }

    override fun showDialogMessage(text: String?, textResId: Int?, listener: Listener?) {
        (activity as BaseActivity).showDialogMessage(text, textResId, listener)
    }

    override fun showDialogQuestion(textResId: Int?, listener: Listener?) {
        (activity as BaseActivity).showDialogQuestion(textResId, listener)
    }

    override fun showLoadingDialog(show: Boolean) {
        (activity as BaseActivity).showLoadingDialog(show)
    }

    override fun finishWithMessage(message: String?) {
        //override if needed
    }

    override fun finishWithResult(result: Int) {
        //override if needed
    }
}
