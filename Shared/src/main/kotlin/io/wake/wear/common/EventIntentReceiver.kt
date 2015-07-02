package io.wake.wear.common

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.app.PendingIntent
import android.util.Log

public class EventIntentReceiver : BroadcastReceiver() {
    private val TAG = "EventIntentReceiver"

    companion object {
        public val ACTION_ENTER: String = "io.wake.wear.ACTION_ENTER"
        public val ACTION_EXIT: String = "io.wake.wear.ACTION_EXIT"
        public val ACTION_WOL: String = "io.wake.wear.ACTION_WOL"

        public fun intent(ctx: Context, action: String): Intent =
                Intent(action).setClass(ctx, javaClass<EventIntentReceiver>())

        public fun broadcastIntent(ctx: Context, action: String): PendingIntent =
                PendingIntent.getBroadcast(ctx, 0, intent(ctx, action), PendingIntent.FLAG_UPDATE_CURRENT)
    }

    override fun onReceive(context: Context, intent: Intent) {
        Log.d(TAG, "Received intent: ${intent.getAction()}")
    }
}