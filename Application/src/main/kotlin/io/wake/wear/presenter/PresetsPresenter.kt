package io.wake.wear.presenter

import io.wake.wear.app.WakeApplication
import io.wake.wear.common.model.Preset
import io.wake.wear.store.PresetStore
import io.wake.wear.ui.view.PresetsView

public class PresetsPresenter(override val view: PresetsView,
                              val presetStore: PresetStore) : Presenter<PresetsView> {

    override fun onResume() {
        super.onResume()
        refresh()
    }

    fun onPresetClicked(item: Preset) {
        view.navigateToPreset(item.getId())
    }

    fun onCreateClicked() {
        view.navigateToCreate()
    }

    private fun refresh() {
        view.showPresets(presetStore.getPresets())
    }
}