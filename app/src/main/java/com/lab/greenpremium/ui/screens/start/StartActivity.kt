package com.lab.greenpremium.ui.screens.start

import android.content.Intent
import android.graphics.LinearGradient
import android.graphics.Shader
import android.net.Uri
import android.support.v4.content.ContextCompat
import android.view.ViewTreeObserver
import com.lab.greenpremium.App
import com.lab.greenpremium.GP_SUPPORT_PHONE_NUMBER
import com.lab.greenpremium.R
import com.lab.greenpremium.ui.screens.base.BaseActivity
import com.lab.greenpremium.utills.setTouchAnimationShrink
import kotlinx.android.synthetic.main.activity_start.*
import javax.inject.Inject

class StartActivity : BaseActivity(), StartContract.View {

    @Inject
    internal lateinit var presenter: StartPresenter

    override fun layoutResId(): Int {
        return R.layout.activity_start
    }

    override fun initializeDaggerComponent() {
        DaggerStartComponent.builder()
                .appComponent((application as App).component)
                .startModule(StartModule(this))
                .build()
                .inject(this)
    }

    override fun initViews() {
        presenter.onViewCreated()

        button_auth.setOnClickListener { presenter.onClickAuth() }
        button_demo.setOnClickListener { presenter.onClickDemo() }
        button_support.setOnClickListener { presenter.onClickSupport() }

        setTouchAnimationShrink(button_auth)
        setTouchAnimationShrink(button_demo)
        setTouchAnimationShrink(button_register)
    }

    override fun initializeGradientTitle() {
        val context = this
        container_main.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                container_main.viewTreeObserver.removeOnGlobalLayoutListener(this)
                title_gradient.paint.shader = LinearGradient(0f, 0f, title_gradient.width * 0.66f, 0f,
                        intArrayOf(ContextCompat.getColor(context, R.color.green_1), ContextCompat.getColor(context, R.color.green_3)),
                        floatArrayOf(0.5f, 1f), Shader.TileMode.CLAMP)
            }
        })
    }

    override fun callToOffice() {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:$GP_SUPPORT_PHONE_NUMBER")
        applicationContext.startActivity(intent)
    }

    override fun goToAuth() {
        goToAuthScreen()
    }

    override fun goToMain() {
        goToMainScreen()
    }
}

