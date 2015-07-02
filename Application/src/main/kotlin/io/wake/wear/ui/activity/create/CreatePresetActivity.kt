package io.wake.wear.ui.activity.create

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import butterknife.bindView
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapFragment
import io.wake.wear.R
import io.wake.wear.app.WakeApplication
import io.wake.wear.presenter.CreatePresetPresenter
import io.wake.wear.store.PresetStore
import io.wake.wear.ui.activity.PresenterActivity
import io.wake.wear.ui.view.CreatePresetView
import pl.charmas.android.reactivelocation.ReactiveLocationProvider
import rx.Observable
import rx.android.view.OnClickEvent
import rx.android.view.ViewObservable
import rx.android.widget.OnTextChangeEvent
import rx.android.widget.WidgetObservable
import javax.inject.Inject

public class CreatePresetActivity : CreatePresetView,
        PresenterActivity<CreatePresetPresenter>(R.layout.activity_preset_create) {

    var store: PresetStore? = null
        [Inject] set

    var location: ReactiveLocationProvider? = null
        [Inject] set

    val mac: EditText by bindView(R.id.et_mac_address)
    val address: EditText by bindView(R.id.et_name)
    val saveButton: Button by bindView(R.id.btn_create_next)

    private var map: GoogleMap? = null

    companion object {
        fun intent(ctx: Context, lat: Double, lng: Double): Intent
                = Intent(ctx, javaClass<CreatePresetActivity>())
                .putExtra("lat", lat)
                .putExtra("lng", lng)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super<PresenterActivity>.onCreate(savedInstanceState)
        WakeApplication.component(this).inject(this)
        init()
    }

    override fun observeMacField(emit: Boolean): Observable<OnTextChangeEvent> = WidgetObservable.text(mac, emit)
    override fun observeAddress(emit: Boolean): Observable<OnTextChangeEvent> = WidgetObservable.text(address, emit)
    override fun observeSave(): Observable<OnClickEvent> = ViewObservable.clicks(saveButton)

    override fun setMacError(error: String) {
        mac.setError(error)
    }

    override fun setAddressError(error: String) {
        address.setError(error)
    }

    override fun saveEnabled(valid: Boolean) {
        saveButton.setEnabled(valid)
    }

    override fun done() {
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.getItemId()) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super<PresenterActivity>.onOptionsItemSelected(item)
    }

    private var latitude: Double = 0.0
    private var longitude: Double = 0.0

    private fun init() {
        saveEnabled(false)
        presenter = CreatePresetPresenter(this, store!!, location!!)
        setTitle(null)
        getSupportActionBar().setDisplayHomeAsUpEnabled(true)
        val mapFragment = getFragmentManager().findFragmentById(R.id.map) as MapFragment
        mapFragment.getMapAsync { map -> this.map = map }
        val intent = getIntent()
        latitude = intent.getDoubleExtra("lat", 0.0)
        longitude = intent.getDoubleExtra("lng", 0.0)
    }
}