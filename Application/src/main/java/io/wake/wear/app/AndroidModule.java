package io.wake.wear.app;

import android.content.Context;
import android.location.LocationManager;
import android.net.wifi.WifiManager;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.wearable.Wearable;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.wake.wear.store.PresetStore;
import pl.charmas.android.reactivelocation.ReactiveLocationProvider;

@Module
public class AndroidModule {
    private final BaseApplication application;

    public AndroidModule(BaseApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return application;
    }

    @Provides
    @Singleton
    LocationManager provideLocationManager() {
        return (LocationManager) application.getSystemService(Context.LOCATION_SERVICE);
    }

    @Provides
    @Singleton
    GoogleApiClient provideGoogleApiClient(Context context) {
        return new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API)
                .addApi(Wearable.API)
                .build();
    }

    @Provides
    @Singleton
    WifiManager providesWifiManager(Context context) {
        return (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    }

    @Provides
    PresetStore providesPresetStore(Context context, GoogleApiClient googleApiClient) {
        return new PresetStore(context);
    }

    @Provides
    ReactiveLocationProvider providesLocationProvider(Context context) {
        return new ReactiveLocationProvider(context);
    }
}