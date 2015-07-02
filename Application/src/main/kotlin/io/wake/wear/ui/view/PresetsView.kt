package io.wake.wear.ui.view

import io.wake.wear.common.model.Preset

public trait PresetsView : PresentationView {
    fun showPresets(presets: List<Preset>)
    fun navigateToPreset(id: Long)
    fun navigateToCreate()
}