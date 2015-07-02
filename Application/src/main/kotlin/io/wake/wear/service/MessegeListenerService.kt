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
import io.wake.wear.service.WakeOnLanService

public class MessegeListenerService : WearableListenerService() {

    private val TAG = "MessageListenerService"

    override fun onMessageReceived(messageEvent: MessageEvent?) {
        super.onMessageReceived(messageEvent)
        Log.d(TAG, "Message: ${messageEvent?.getPath()}")
        when (messageEvent?.getPath()) {
            MessageConstants.REQUEST_WOL -> startService(WakeOnLanService.intent(this))
            else -> Log.d(TAG, "Unknown path")
        }
    }
}