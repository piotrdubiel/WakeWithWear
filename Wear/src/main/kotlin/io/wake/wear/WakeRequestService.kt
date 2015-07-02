package io.wake.wear

import android.app.IntentService
import android.content.Intent
import android.content.Context
import android.app.PendingIntent
import com.google.android.gms.common.api.GoogleApiClient
import javax.inject.Inject
import io.wake.wear.app.WearApplication
import com.google.android.gms.wearable.Wearable
import io.wake.wear.common.MessageConstants
import android.support.v4.app.NotificationManagerCompat
import android.util.Log
import io.timesheet.views.Confirmation

public class WakeRequestService : IntentService("WakeRequestService") {

    private val TAG = javaClass.getSimpleName()

    var googleApiClient: GoogleApiClient? = null
        [Inject] set

    companion object {
        fun intent(ctx: Context): Intent =
                Intent(ctx, javaClass<WakeRequestService>())

        fun pendingIntent(ctx: Context): PendingIntent =
                PendingIntent.getService(ctx, 0, intent(ctx), PendingIntent.FLAG_UPDATE_CURRENT)
    }

    override fun onCreate() {
        super.onCreate()
        WearApplication.component(this).inject(this)
    }

    override fun onHandleIntent(intent: Intent?) {
        Log.d(TAG, "intent")
        NotificationManagerCompat.from(this).cancelAll()
        googleApiClient!!.blockingConnect()
        val nodes = Wearable.NodeApi.getConnectedNodes(googleApiClient).await()
        nodes.getNodes().forEach {
            Wearable.MessageApi.sendMessage(googleApiClient, it.getId(),
                    MessageConstants.REQUEST_WOL, byteArray(0))
        }
        Confirmation(this).success()
    }
}