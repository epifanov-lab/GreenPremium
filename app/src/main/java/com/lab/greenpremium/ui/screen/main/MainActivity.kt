package com.lab.greenpremium.ui.screen.main

import android.animation.Animator
import android.content.Intent
import android.os.Handler
import android.support.design.widget.BottomNavigationView
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewAnimationUtils
import com.getbase.floatingactionbutton.FloatingActionsMenu
import com.lab.greenpremium.*
import com.lab.greenpremium.data.MeetingAddedEvent
import com.lab.greenpremium.data.UserModel
import com.lab.greenpremium.ui.components.BottomNavigationViewHelper
import com.lab.greenpremium.ui.screen.base.BaseActivity
import com.lab.greenpremium.ui.screen.main.cart.CartFragment
import com.lab.greenpremium.ui.screen.main.contacts.ContactsFragment
import com.lab.greenpremium.ui.screen.main.favorites.FavoritesFragment
import com.lab.greenpremium.ui.screen.main.map.MapFragment
import com.lab.greenpremium.ui.screen.main.plants.PlantsFragment
import com.lab.greenpremium.ui.screen.main.portfolio.PortfolioFragment
import com.lab.greenpremium.ui.screen.main.profile.ProfileFragment
import com.lab.greenpremium.ui.screen.message.MessageScreenType
import com.lab.greenpremium.utills.eventbus.BaseEvent
import com.lab.greenpremium.utills.eventbus.PlantCountChangedEvent
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject


class MainActivity : BaseActivity(), MainContract.View {

    @Inject
    internal lateinit var presenter: MainPresenter

    override fun layoutResId(): Int {
        return R.layout.activity_main
    }

    override fun initializeDaggerComponent() {
        DaggerMainComponent.builder()
                .appComponent((application as App).component)
                .mainModule(MainModule(this))
                .build()
                .inject(this)
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->

        BottomNavigationViewHelper.setUncheckable(navigation, false)
        button_favorite.setImageResource(R.drawable.ic_favorites)
        button_cart.setImageResource(R.drawable.ic_cart)

        when (item.itemId) {
            R.id.nav_profile -> {
                title_text.setText(R.string.screen_title_profile)
                swapFragment(ProfileFragment.newInstance())
                activateFabMenu(true)
                return@OnNavigationItemSelectedListener true
            }

            R.id.nav_plants -> {
                title_text.setText(R.string.screen_title_plants)
                swapFragment(PlantsFragment.newInstance())
                activateFabMenu(false)
                return@OnNavigationItemSelectedListener true
            }

            R.id.nav_portfolio -> {
                title_text.setText(R.string.screen_title_portfolio)
                swapFragment(PortfolioFragment.newInstance())
                activateFabMenu(false)
                return@OnNavigationItemSelectedListener true
            }

            R.id.nav_contacts -> {
                title_text.setText(R.string.screen_title_contacts)
                swapFragment(ContactsFragment.newInstance())
                activateFabMenu(false)
                return@OnNavigationItemSelectedListener true
            }

            R.id.nav_map -> {
                title_text.setText(R.string.screen_title_map)
                swapFragment(MapFragment.newInstance())
                activateFabMenu(false)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun initViews() {
        swapFragment(ProfileFragment.newInstance())

        navigation.itemIconTintList = null
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        BottomNavigationViewHelper.disableShiftMode(navigation)

        button_favorite.setOnClickListener {
            title_text.setText(R.string.screen_title_favorites)
            button_favorite.setImageResource(R.drawable.ic_favorites_choosen)
            button_cart.setImageResource(R.drawable.ic_cart)
            swapFragment(FavoritesFragment.newInstance())
            BottomNavigationViewHelper.setUncheckable(navigation, true)
            activateFabMenu(false)
        }

        button_cart.setOnClickListener {
            title_text.setText(R.string.screen_title_basket)
            button_cart.setImageResource(R.drawable.ic_basket_choosen)
            button_favorite.setImageResource(R.drawable.ic_favorites)
            swapFragment(CartFragment.newInstance())
            BottomNavigationViewHelper.setUncheckable(navigation, true)
            activateFabMenu(false)
        }

        activateFabMenu(true)
        fab_project.setOnClickListener { goToMessageScreen(MessageScreenType.NEW_PROJECT).also { fab_menu.collapse() } }
        fab_letter.setOnClickListener { goToMessageScreen(MessageScreenType.LETTER).also { fab_menu.collapse() } }
        fab_praise.setOnClickListener { goToMessageScreen(MessageScreenType.PRAISE).also { fab_menu.collapse() } }
        fab_complain.setOnClickListener { goToMessageScreen(MessageScreenType.COMPLAIN).also { fab_menu.collapse() } }
    }

    private fun activateFabMenu(enabled: Boolean) {
        fab_menu.visibility = if (enabled) VISIBLE else GONE
        if (!enabled) {
            fab_menu.collapse()
            obstructor.visibility = GONE
        }

        fab_menu.setOnFloatingActionsMenuUpdateListener(object : FloatingActionsMenu.OnFloatingActionsMenuUpdateListener {
            override fun onMenuExpanded() {
                val x = obstructor.right
                val y = obstructor.bottom
                val startRadius = 0
                val endRadius = Math.hypot(obstructor.width.toDouble(), obstructor.height.toDouble())
                val animation = ViewAnimationUtils.createCircularReveal(obstructor, x, y, startRadius.toFloat(), endRadius.toFloat())
                animation.duration = DURATION_FAST
                obstructor.visibility = View.VISIBLE
                animation.start()
            }

            override fun onMenuCollapsed() {
                val x = obstructor.right
                val y = obstructor.bottom
                val startRadius = Math.max(obstructor.width, obstructor.height)
                val endRadius = 0
                val animation = ViewAnimationUtils.createCircularReveal(obstructor, x, y, startRadius.toFloat(), endRadius.toFloat())
                animation.duration = DURATION_FAST
                animation.addListener(object : Animator.AnimatorListener {
                    override fun onAnimationRepeat(p0: Animator?) {
                        //ignore
                    }

                    override fun onAnimationEnd(p0: Animator?) {
                        obstructor.visibility = View.GONE
                    }

                    override fun onAnimationCancel(p0: Animator?) {
                        //ignore
                    }

                    override fun onAnimationStart(p0: Animator?) {
                        //ignore
                    }
                })
                animation.start()
            }

        })
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: BaseEvent) {
        when (event) {
            is PlantCountChangedEvent -> button_cart.updateIndicator(UserModel.getCountOfItemsInCart())
        }
    }

    public override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    public override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    override fun onBackPressed() {
        finishAffinity()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            KEY_RESULT_ADD_MEETING -> {
                Handler().post {
                    EventBus.getDefault().post(MeetingAddedEvent())
                    val message = data!!.getStringExtra(KEY_OBJECT)
                    showSnackbar(message)
                }
            }

        }
    }
}
