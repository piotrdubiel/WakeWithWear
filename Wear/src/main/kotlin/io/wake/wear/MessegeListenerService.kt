package io.wake.wear

import com.google.android.gms.wearable.WearableListenerService
import com.google.android.gms.wearable.MessageEvent
import android.app.NotificationManager
import io.wake.wear.common.MessageConstants
import android.app.Notification
import android.support.v4.app.NotificationCompat
import android.content.Context
import android.support.v4.app.NotificationManagerCompat
import android.content.Intent
import io.wake.wear.common.EventIntentReceiver
import io.wake.wear.common.R
import android.util.Log
import io.wake.wear.common.model.ParcelablePreset
import io.wake.wear.common.model.Preset

public class MessegeListenerService : WearableListenerService() {

    private val TAG = "MessageListenerService"

    override fun onMessageReceived(messageEvent: MessageEvent?) {
        super.onMessageReceived(messageEvent)
        Log.d(TAG, "Message: ${messageEvent?.getPath()}")
        val preset = ParcelablePreset.unmarshall(messageEvent?.getData()).getPreset()
        when (messageEvent?.getPath()) {
            MessageConstants.EVENT_ENTER -> showNotification(preset)
            MessageConstants.EVENT_EXIT -> cancelNotification(preset)
            else -> Log.d(TAG, "Unknown path")
        }
    }

    private fun showNotification(preset: Preset) {
        val builder = Notification.Builder(this)

        val actionIntent = WakeRequestService.pendingIntent(this)
        val action = Notification.Action(R.drawable.ic_desktop_windows_green_24dp, "", actionIntent)

        val wearableOptions = Notification.WearableExtender()
                .setHintHideIcon(true)
                .addAction(action)
                .setContentAction(0)

        builder.setContentTitle("Turn on PC?")
                .setContentText("At ${preset.getName()}")
                .setSmallIcon(R.drawable.ic_desktop_windows_black_48dp)
                .setColor(getResources().getColor(R.color.primary_color))
                .setVibrate(longArray(0, 50))
                .setPriority(NotificationCompat.PRIORITY_HIGH)

        builder.extend(wearableOptions)

        (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).notify(2, builder.build())
    }

    private fun cancelNotification(preset: Preset) {
        NotificationManagerCompat.from(this).cancel(preset.getId() as Int)
    }
}