package io.wake.wear.app;

import javax.inject.Singleton;

import dagger.Component;
import io.wake.wear.ui.activity.create.CreatePresetActivity;
import io.wake.wear.presenter.PresetsPresenter;
import io.wake.wear.ui.activity.DetailActivity;
import io.wake.wear.ui.activity.PresetsActivity;
import io.wake.wear.service.TransitionService;
import io.wake.wear.store.PresetStore;
import io.wake.wear.ui.fragement.PickAddressFragment;

@Singleton
@Component(modules = AndroidModule.class)
public interface ApplicationComponent {
    void inject(WakeApplication application);
    void inject(PresetsActivity activity);
    void inject(CreatePresetActivity activity);
    void inject(PickAddressFragment fragment);
    void inject(DetailActivity activity);
    void inject(TransitionService service);
    void inject(PresetStore store);
    void inject(PresetsPresenter presenter);
}