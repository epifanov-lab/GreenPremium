package com.lab.greenpremium.ui.screens.main.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lab.greenpremium.App
import com.lab.greenpremium.GP_OFFICE_POINT
import com.lab.greenpremium.R
import com.lab.greenpremium.YANDEX_MAP_KEY
import com.lab.greenpremium.data.entity.Feature
import com.lab.greenpremium.ui.screens.base.BaseFragment
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.IconStyle
import com.yandex.runtime.image.ImageProvider
import kotlinx.android.synthetic.main.fragment_map.*
import javax.inject.Inject


class MapFragment : BaseFragment(), MapContract.View {
    @Inject
    internal lateinit var presenter: MapPresenter

    companion object {

        fun newInstance() = MapFragment()
    }

    override fun initializeDaggerComponent() {
        DaggerMapComponent.builder()
                .appComponent((activity?.application as App).component)
                .mapModule(MapModule(this))
                .build()
                .inject(this)
    }

    override fun layoutResId(): Int {
        return com.lab.greenpremium.R.layout.fragment_map
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        MapKitFactory.setApiKey(YANDEX_MAP_KEY)
        MapKitFactory.initialize(context)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun initViews() {
        presenter.onViewCreated()

        map.map.move(
                CameraPosition(GP_OFFICE_POINT, 15.0f, 0.0f, 0.0f),
                Animation(Animation.Type.SMOOTH, 2f),
                null)
    }

    override fun placeMarkers(features: List<Feature>) {
        val mapObjects = map.map.mapObjects
        for ((index, feature) in features.withIndex()) {
            val point = Point(feature.geometry.coordinates[0], feature.geometry.coordinates[1])
            val marker = mapObjects.addPlacemark(point)

            if (index != features.lastIndex) {
                marker.setIcon(ImageProvider.fromResource(context, R.drawable.ic_map_marker))

            } else {
                marker.setIcon(ImageProvider.fromResource(context, R.drawable.ic_map_marker),
                        IconStyle(null, null, null, null, true, 2f, null))
            }
        }
    }

    override fun onStop() {
        super.onStop()
        map.onStop()
        MapKitFactory.getInstance().onStop()
    }

    override fun onStart() {
        super.onStart()
        map.onStart()
        MapKitFactory.getInstance().onStart()
    }

}
