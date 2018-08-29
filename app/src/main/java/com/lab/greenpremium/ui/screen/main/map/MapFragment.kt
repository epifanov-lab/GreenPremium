package com.lab.greenpremium.ui.screen.main.map

import android.os.Bundle
import android.view.View
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.MarkerOptions
import com.lab.greenpremium.GP_OFFICE_LOCATION
import com.lab.greenpremium.R
import com.lab.greenpremium.ui.screen.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_map.*


class MapFragment : BaseFragment() {

    companion object {
        fun newInstance() = MapFragment()
    }

    override fun initializeDaggerComponent() {
        //TODO impl
    }

    override fun layoutResId(): Int {
        return R.layout.fragment_map
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        map_view.onCreate(savedInstanceState)
        map_view.onResume()

        try {
            MapsInitializer.initialize(activity!!.applicationContext)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        map_view.getMapAsync { map ->

            // For dropping a marker at a point on the Map
            map.addMarker(MarkerOptions().position(GP_OFFICE_LOCATION).title("Офис Green Premium")
            .snippet("Бизнес-центр «Manhattan», офис 211\n" +
                    "Адрес: 105066, г. Москва, ул. Нижняя Красносельская, д. 35, стр. 9\n" +
                    "Режим работы: пн-пт с 10 до 19 часов\n" +
                    "Телефон: +7 495 380-39-59, 8-800-775-70-75\n" +
                    "Факс: +7 495 380-39-59\n" +
                    "E-mail: sale@greenpremium.ru\n" +
                    "https://greenpremium.ru/")) //TODO довести до ума (поле с инфой маленькое)

            // For zooming automatically to the location of the marker
            val cameraPosition = CameraPosition.Builder().target(GP_OFFICE_LOCATION).zoom(15f).build()
            map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
        }

        super.onViewCreated(view, savedInstanceState)
    }

    override fun initViews() {
        //ignore
    }

    override fun onResume() {
        super.onResume()
        map_view.onResume()
    }

    override fun onPause() {
        super.onPause()
        map_view.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        map_view.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        map_view.onLowMemory()
    }
}
