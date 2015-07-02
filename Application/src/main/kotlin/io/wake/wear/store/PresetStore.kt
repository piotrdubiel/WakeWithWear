package io.wake.wear.store

import android.content.Context
import android.util.Log
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import io.realm.Realm
import io.wake.wear.GEOFENCE_RADIUS_IN_METERS
import io.wake.wear.app.WakeApplication
import io.wake.wear.common.model.Preset
import io.wake.wear.service.TransitionService
import javax.inject.Inject

public class PresetStore(val context: Context) {
    val realm: Realm = Realm.getInstance(context)

    public fun create(preset: Preset) {
        realm.beginTransaction()

        val nextId = realm.where(javaClass<Preset>()).maximumInt("id") + 1
        preset.setId(nextId)
        realm.copyToRealm(preset)
        realm.commitTransaction()
    }

    public fun getPresets(): List<Preset> {
        val query = realm.where(javaClass<Preset>())
        val result = query.findAll()
        return result.toLinkedList()
    }

    public fun findPresetWithId(id: Long): Preset =
            realm.where(javaClass<Preset>()).equalTo("id", id).findFirst()

    public fun delete(preset: Preset) {
        realm.beginTransaction()
        preset.removeFromRealm()
        realm.commitTransaction()
    }


}