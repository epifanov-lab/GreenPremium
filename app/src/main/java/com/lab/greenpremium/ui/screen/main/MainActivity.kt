package com.lab.greenpremium.ui.screen.main

import android.support.design.widget.BottomNavigationView
import com.lab.greenpremium.R
import com.lab.greenpremium.ui.base.BaseActivity
import com.lab.greenpremium.ui.screen.main.basket.BasketFragment
import com.lab.greenpremium.ui.screen.main.contacts.ContactsFragment
import com.lab.greenpremium.ui.screen.main.favorites.FavoritesFragment
import com.lab.greenpremium.ui.screen.main.map.MapFragment
import com.lab.greenpremium.ui.screen.main.plants.PlantFragment
import com.lab.greenpremium.ui.screen.main.portfolio.PortfolioFragment
import com.lab.greenpremium.ui.screen.main.profile.ProfileFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun layoutResId(): Int {
        return R.layout.activity_main
    }

    override fun initializeDaggerComponent() {
        //ignore
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->

        BottomNavigationViewHelper.setUncheckable(navigation, false)
        button_favorites.setImageResource(R.drawable.ic_favorites)
        button_basket.setImageResource(R.drawable.ic_basket)

        when (item.itemId) {
            R.id.nav_plants -> {
                message.setText(R.string.title_plants)
                swapFragment(PlantFragment.newInstance())
                return@OnNavigationItemSelectedListener true
            }

            R.id.nav_portfolio -> {
                message.setText(R.string.title_portfolio)
                swapFragment(PortfolioFragment.newInstance())
                return@OnNavigationItemSelectedListener true
            }

            R.id.nav_profile -> {
                message.setText(R.string.title_profile)
                swapFragment(ProfileFragment.newInstance())
                return@OnNavigationItemSelectedListener true
            }

            R.id.nav_contacts -> {
                message.setText(R.string.title_contacts)
                swapFragment(ContactsFragment.newInstance())
                return@OnNavigationItemSelectedListener true
            }

            R.id.nav_map -> {
                message.setText(R.string.title_map)
                swapFragment(MapFragment.newInstance())
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun initViews() {
        swapFragment(PlantFragment.newInstance())

        navigation.itemIconTintList = null
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        BottomNavigationViewHelper.disableShiftMode(navigation)

        button_favorites.setOnClickListener {
            message.setText(R.string.title_favorites)
            button_favorites.setImageResource(R.drawable.ic_favorites_choosen)
            button_basket.setImageResource(R.drawable.ic_basket)
            swapFragment(FavoritesFragment.newInstance())
            BottomNavigationViewHelper.setUncheckable(navigation, true)
        }

        button_basket.setOnClickListener {
            message.setText(R.string.title_basket)
            button_basket.setImageResource(R.drawable.ic_basket_choosen)
            button_favorites.setImageResource(R.drawable.ic_favorites)
            swapFragment(BasketFragment.newInstance())
            BottomNavigationViewHelper.setUncheckable(navigation, true)
        }
    }

    override fun onBackPressed() {
        finishAffinity()
    }
}
