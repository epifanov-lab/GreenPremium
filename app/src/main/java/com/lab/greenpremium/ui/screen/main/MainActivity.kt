package com.lab.greenpremium.ui.screen.main

import android.animation.Animator
import android.support.design.widget.BottomNavigationView
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewAnimationUtils
import com.getbase.floatingactionbutton.FloatingActionsMenu
import com.lab.greenpremium.DURATION_FAST
import com.lab.greenpremium.R
import com.lab.greenpremium.data.repository.UserModel
import com.lab.greenpremium.ui.components.BottomNavigationViewHelper
import com.lab.greenpremium.ui.screen.base.BaseActivity
import com.lab.greenpremium.ui.screen.main.cart.CartFragment
import com.lab.greenpremium.ui.screen.main.contacts.ContactsFragment
import com.lab.greenpremium.ui.screen.main.favorites.FavoritesFragment
import com.lab.greenpremium.ui.screen.main.map.MapFragment
import com.lab.greenpremium.ui.screen.main.plants.PlantFragment
import com.lab.greenpremium.ui.screen.main.portfolio.PortfolioFragment
import com.lab.greenpremium.ui.screen.main.profile.ProfileFragment
import com.lab.greenpremium.ui.screen.message.MessageScreenType
import com.lab.greenpremium.utills.eventbus.BaseEvent
import com.lab.greenpremium.utills.eventbus.PlantCountChangedEvent
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class MainActivity : BaseActivity() {

    override fun layoutResId(): Int {
        return R.layout.activity_main
    }

    override fun initializeDaggerComponent() {
        //ignore
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->

        BottomNavigationViewHelper.setUncheckable(navigation, false)
        button_favorite.setImageResource(R.drawable.ic_favorites)
        button_cart.setImageResource(R.drawable.ic_cart)

        when (item.itemId) {
            R.id.nav_profile -> {
                message.setText(R.string.title_profile)
                swapFragment(ProfileFragment.newInstance())
                activateFabMenu(true)
                return@OnNavigationItemSelectedListener true
            }

            R.id.nav_plants -> {
                message.setText(R.string.title_plants)
                swapFragment(PlantFragment.newInstance())
                activateFabMenu(false)
                return@OnNavigationItemSelectedListener true
            }

            R.id.nav_portfolio -> {
                message.setText(R.string.title_portfolio)
                swapFragment(PortfolioFragment.newInstance())
                activateFabMenu(false)
                return@OnNavigationItemSelectedListener true
            }

            R.id.nav_contacts -> {
                message.setText(R.string.title_contacts)
                swapFragment(ContactsFragment.newInstance())
                activateFabMenu(false)
                return@OnNavigationItemSelectedListener true
            }

            R.id.nav_map -> {
                message.setText(R.string.title_map)
                swapFragment(MapFragment.newInstance())
                activateFabMenu(false)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun initViews() {
        swapFragment(ProfileFragment.newInstance())
        activateFabMenu(true)

        navigation.itemIconTintList = null
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        BottomNavigationViewHelper.disableShiftMode(navigation)

        button_favorite.setOnClickListener {
            message.setText(R.string.title_favorites)
            button_favorite.setImageResource(R.drawable.ic_favorites_choosen)
            button_cart.setImageResource(R.drawable.ic_cart)
            swapFragment(FavoritesFragment.newInstance())
            BottomNavigationViewHelper.setUncheckable(navigation, true)
            activateFabMenu(false)
        }

        button_cart.setOnClickListener {
            message.setText(R.string.title_basket)
            button_cart.setImageResource(R.drawable.ic_basket_choosen)
            button_favorite.setImageResource(R.drawable.ic_favorites)
            swapFragment(CartFragment.newInstance())
            BottomNavigationViewHelper.setUncheckable(navigation, true)
            activateFabMenu(false)
        }

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
}
