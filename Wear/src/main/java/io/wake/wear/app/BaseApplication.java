package io.wake.wear.app;

import android.app.Application;

public class BaseApplication extends Application {
    protected ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        /**
         * This initialization cannot be moved to Kotlin.
         * Compiler will fail with Unresolved reference error: Dagger_ApplicationComponent
         */
        component = DaggerApplicationComponent.builder()
                .androidModule(new AndroidModule(this)).build();
    }

    public ApplicationComponent getComponent() {
        return component;
    }
}
