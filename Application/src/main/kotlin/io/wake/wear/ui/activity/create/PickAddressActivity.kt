package io.wake.wear.ui.activity.create

import android.content.Context
import android.content.Intent
import android.location.Geocoder
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import butterknife.bindView
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import io.wake.wear.R
import io.wake.wear.presenter.PickAddressPresenter
import io.wake.wear.ui.activity.PresenterActivity
import io.wake.wear.ui.view.PickAddressView
import io.wake.wear.ui.view.singleClick
import java.util.Locale

public class PickAddressActivity : PickAddressView,
        PresenterActivity<PickAddressPresenter>(R.layout.activity_create_address) {

    val latitudeView: TextView by bindView(R.id.tv_address_latitude)
    val longitudeView: TextView by bindView(R.id.tv_address_longitude)
    val next: Button by bindView(R.id.btn_create_next)

    companion object {
        fun intent(ctx: Context): Intent = Intent(ctx, javaClass<PickAddressActivity>())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super<PresenterActivity>.onCreate(savedInstanceState)
        setTitle("Choose location")
        presenter = PickAddressPresenter(this)
        getSupportActionBar().setDisplayHomeAsUpEnabled(true)
        val mapFragment = getFragmentManager().findFragmentById(R.id.map) as MapFragment
        mapFragment.getMapAsync { map ->
            map.setOnMapLongClickListener { position ->
                val marker = map.addMarker(MarkerOptions().position(position).draggable(true))
                updatePosition(marker!!)
            }

        }
        next.singleClick { navigateToNextStep() }
    }

    private fun updatePosition(marker: Marker) {
        val position = marker.getPosition()
        latitudeView.setText(position.latitude.toString())
        longitudeView.setText(position.longitude.toString())
        presenter.setPosition(position.latitude, position.longitude)
    }

    private fun navigateToNextStep() {
        val (latitude, longitude) = presenter.getPosition()
        startActivity(CreatePresetActivity.intent(this, latitude, longitude))
    }
}