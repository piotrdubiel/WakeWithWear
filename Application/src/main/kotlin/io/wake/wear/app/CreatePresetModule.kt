package io.wake.wear.app

import io.wake.wear.presenter.CreatePresetPresenter
import io.wake.wear.store.PresetStore
import io.wake.wear.ui.view.CreatePresetView
import pl.charmas.android.reactivelocation.ReactiveLocationProvider
import dagger.Module as module
import dagger.Provides as provides
import javax.inject.Singleton as singleton

public module class CreatePresetModule(val view: CreatePresetView) {

    provides fun providesCreatePresetPresenter(store: PresetStore, observableLocation: ReactiveLocationProvider): CreatePresetPresenter {
        return CreatePresetPresenter(view, store, observableLocation)
    }
}