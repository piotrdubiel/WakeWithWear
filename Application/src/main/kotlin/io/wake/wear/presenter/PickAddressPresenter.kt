package io.wake.wear.presenter

import io.wake.wear.ui.view.PickAddressView
import kotlin.properties.Delegates

public class PickAddressPresenter(override val view: PickAddressView) : Presenter<PickAddressView> {
    private var latitude: Double by Delegates.notNull()
    private var longitude: Double by Delegates.notNull()

    fun setPosition(latitude: Double, longitude: Double) {
        this.latitude = latitude
        this.longitude = longitude
    }

    fun getPosition(): Pair<Double, Double> = Pair(latitude, longitude)
}