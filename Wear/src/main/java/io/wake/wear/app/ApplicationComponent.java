package io.wake.wear.app;

import javax.inject.Singleton;

import dagger.Component;
import io.wake.wear.WakeRequestService;

@Singleton
@Component(modules = AndroidModule.class)
public interface ApplicationComponent {
    void inject(WearApplication application);
    void inject(WakeRequestService service);
}