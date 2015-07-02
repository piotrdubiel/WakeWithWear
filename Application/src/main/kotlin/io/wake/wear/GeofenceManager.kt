package io.wake.wear

import android.content.Context
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import io.wake.wear.common.model.Preset
import io.wake.wear.service.TransitionService
import rx.Observable
import rx.schedulers.Schedulers
import rx.wear.connect.Connected
import rx.wear.connect.ConnectionEvent
import rx.wear.connect.OnSubscribeWearableConnection
import com.google.android.gms.common.api.Status

public class GeofenceManager(val context: Context, val googleApiClient: GoogleApiClient) {

    init {
        val observable = Observable.create<ConnectionEvent>(OnSubscribeWearableConnection(googleApiClient))
                .subscribeOn(Schedulers.newThread())
//                .filter { it is Connected }.subscribe().

    }

//    public fun add(preset: Preset): Observable<Status> {
//        Observable.create<ConnectionEvent>(OnSubscribeWearableConnection(googleApiClient))
//                .subscribeOn(Schedulers.newThread())
//                .filter { it is Connected }
//    }

    private fun addGeofence(preset: Preset) {
        val geofence = Geofence.Builder()
                .setRequestId(preset.getId().toString())
                .setCircularRegion(
                        preset.getLatitude(),
                        preset.getLongitude(),
                        GEOFENCE_RADIUS_IN_METERS
                )
                .setExpirationDuration(Geofence.NEVER_EXPIRE)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER or Geofence.GEOFENCE_TRANSITION_EXIT)
                .build()

        LocationServices.GeofencingApi.addGeofences(
                googleApiClient,
                geofencingRequest(geofence),
                TransitionService.pendingIntent(context))
    }

    private fun geofencingRequest(geofence: Geofence): GeofencingRequest =
            GeofencingRequest.Builder()
                    .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
                    .addGeofence(geofence).build();
}