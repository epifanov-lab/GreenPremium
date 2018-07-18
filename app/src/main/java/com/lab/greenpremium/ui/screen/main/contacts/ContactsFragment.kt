package com.lab.greenpremium.ui.screen.main.contacts

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lab.greenpremium.R


class ContactsFragment : Fragment() {

    companion object {
        val TAG: String = ContactsFragment::class.java.simpleName
        fun newInstance() = ContactsFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_contacts, container, false)
    }
}