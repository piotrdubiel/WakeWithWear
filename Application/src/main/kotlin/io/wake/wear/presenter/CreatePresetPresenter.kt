package io.wake.wear.presenter

import io.wake.wear.common.model.Preset
import io.wake.wear.store.PresetStore
import io.wake.wear.ui.view.CreatePresetView
import pl.charmas.android.reactivelocation.ReactiveLocationProvider
import rx.Observable
import rx.Subscription

public class CreatePresetPresenter(override val view: CreatePresetView,
                                   val store: PresetStore,
                                   val observableLocation: ReactiveLocationProvider) : Presenter<CreatePresetView> {

    private fun validateMac() = view.observeMacField(false).map {
        val macValid = it.text().toString().matches("^([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})$")
        if (!macValid) {
            view.setMacError("Invalid Mac")
        }
        macValid
    }

    private fun validateAddress() = view.observeAddress(false).map {
        val addressValid = it.text().length() > 0
        if (!addressValid) {
            view.setAddressError("Invalid Address")
        }
        addressValid
    }

    private var validation: Subscription? = null
    private var action: Subscription? = null

    override fun onResume() {
        super.onResume()
        centerMap()
        subscribeValidation()
        subscribeSave()
    }

    override fun onPause() {
        super.onPause()
        validation?.unsubscribe()
        action?.unsubscribe()
    }

    private fun subscribeSave() {
        action = view.observeSave()
                .flatMap { fieldsToPreset() }
                .subscribe {
                    save(it)
                    view.done()
                }
    }

    private fun subscribeValidation() {
        validation = Observable.combineLatest(validateMac(), validateAddress(), { macValid, addressValid ->
            macValid and addressValid
        }).subscribe { valid ->
            view.saveEnabled(valid)
        }
    }

    private fun fieldsToPreset(): Observable<Preset> =
            Observable.combineLatest(view.observeMacField(true), view.observeAddress(true), { mac, address ->
                Preset(address.text().toString(), 52.225026, 21.007987, mac.text().toString())
            })

    private fun centerMap() {
        observableLocation.getLastKnownLocation().subscribe { location ->
            //            location.
        }
    }

    private fun save(preset: Preset) {
        store.create(preset)
    }
}