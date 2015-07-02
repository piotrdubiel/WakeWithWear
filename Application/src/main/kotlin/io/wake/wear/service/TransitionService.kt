package io.wake.wear.service

import android.app.IntentService
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Parcel
import android.util.Log
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent
import com.google.android.gms.wearable.Wearable
import io.wake.wear.app.WakeApplication
import io.wake.wear.common.MessageConstants
import io.wake.wear.common.model.ParcelablePreset
import io.wake.wear.common.model.Preset
import io.wake.wear.store.PresetStore
import javax.inject.Inject

public class TransitionService : IntentService("TransitionService") {

    private val TAG = javaClass.getSimpleName()

    var googleApiClient: GoogleApiClient? = null
        [Inject] set

    var presetStore: PresetStore? = null
        [Inject] set

    companion object {
        fun intent(ctx: Context): Intent =
                Intent(ctx, javaClass<TransitionService>())

        fun pendingIntent(ctx: Context): PendingIntent =
                PendingIntent.getService(ctx, 0, intent(ctx), PendingIntent.FLAG_UPDATE_CURRENT)
    }

    override fun onCreate() {
        super.onCreate()
        WakeApplication.component(this).inject(this)
    }

    override fun onHandleIntent(intent: Intent?) {
        val geofencingEvent = GeofencingEvent.fromIntent(intent)

        if (geofencingEvent.hasError()) {
            Log.e(TAG, "Geofence error: ${geofencingEvent.getErrorCode()}")
            return
        }
        Log.d(TAG, "Geofence transition")
        geofencingEvent.getTriggeringGeofences().forEach {
            val preset = presetStore!!.findPresetWithId(it.getRequestId().toLong())

            when (geofencingEvent.getGeofenceTransition()) {
                Geofence.GEOFENCE_TRANSITION_ENTER -> eventEnter(preset)
                Geofence.GEOFENCE_TRANSITION_EXIT -> eventExit(preset)
            }
        }

    }

    private fun eventEnter(preset: Preset) {
        send(MessageConstants.EVENT_ENTER, preset)
    }

    private fun eventExit(preset: Preset) {
        send(MessageConstants.EVENT_EXIT, preset)
    }

    private fun send(path: String, preset: Preset) {
        googleApiClient!!.blockingConnect()
        val nodes = Wearable.NodeApi.getConnectedNodes(googleApiClient).await()
        nodes.getNodes().forEach {
            Wearable.MessageApi.sendMessage(googleApiClient, it.getId(), path, ParcelablePreset(preset).marshall())
        }
    }
}