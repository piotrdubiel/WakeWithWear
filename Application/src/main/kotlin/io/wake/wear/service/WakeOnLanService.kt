package io.wake.wear.service

import android.app.IntentService
import android.content.Intent
import android.content.Context
import android.app.PendingIntent
import android.support.v4.app.NotificationManagerCompat
import android.util.Log
import io.wake.wear.MagicPacket
import io.wake.wear.MAC

public class WakeOnLanService : IntentService("WakeOnLanService") {

    private val TAG = javaClass.getSimpleName()

    companion object {
        fun intent(ctx: Context): Intent =
                Intent(ctx, javaClass<WakeOnLanService>())

        fun pendingIntent(ctx: Context): PendingIntent =
                PendingIntent.getService(ctx, 0, intent(ctx), PendingIntent.FLAG_UPDATE_CURRENT)
    }

    override fun onHandleIntent(intent: Intent?) {
        Log.d(TAG, "intent")
        MagicPacket(MAC, MagicPacket.BROADCAST).send()
    }
}