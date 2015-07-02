package io.wake.wear.presenter

import io.wake.wear.common.model.Preset
import io.wake.wear.getStaticMapUrl
import io.wake.wear.store.PresetStore
import io.wake.wear.ui.view.DetailView
import rx.Subscription

public class DetailPresenter(override val view: DetailView,
                             val store: PresetStore) : Presenter<DetailView> {

    private var preset: Preset? = null

    public fun init(id: Long) {
        preset = store.findPresetWithId(id)
    }

    public fun getMapUrl(): String
            = getStaticMapUrl(preset!!.getLatitude(), preset!!.getLongitude())

    public fun performDelete() {
        store.delete(preset!!)
        view.done()
    }
}