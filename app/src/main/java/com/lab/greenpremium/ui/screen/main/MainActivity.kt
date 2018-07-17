package com.lab.greenpremium.ui.screen.main

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import com.lab.greenpremium.R
import com.lab.greenpremium.ui.base.BaseActivity

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.nav_plants -> {
                message.setText(R.string.title_plants)
                return@OnNavigationItemSelectedListener true
            }

            R.id.nav_portfolio -> {
                message.setText(R.string.title_portfolio)
                return@OnNavigationItemSelectedListener true
            }

            R.id.nav_profile -> {
                message.setText(R.string.title_profile)
                return@OnNavigationItemSelectedListener true
            }

            R.id.nav_contacts -> {
                message.setText(R.string.title_contacts)
                return@OnNavigationItemSelectedListener true
            }

            R.id.nav_map -> {
                message.setText(R.string.title_map)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.itemIconTintList = null
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }
}
