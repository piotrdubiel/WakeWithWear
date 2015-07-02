package io.timesheet.views

import android.content.Intent
import android.content.Context
import android.support.wearable.activity.ConfirmationActivity

public class Confirmation(val ctx: Context) {
    private fun show(animationType: Int, message: String? = null) {
        val intent = Intent(ctx, javaClass<ConfirmationActivity>())
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra(ConfirmationActivity.EXTRA_ANIMATION_TYPE, animationType)
        if (message != null) {
            intent.putExtra(ConfirmationActivity.EXTRA_MESSAGE, message)
        }
        ctx.startActivity(intent)
    }

    fun success(message: String? = null) = show(ConfirmationActivity.SUCCESS_ANIMATION, message)
    fun failure(message: String? = null) = show(ConfirmationActivity.FAILURE_ANIMATION, message)
    fun open(message: String? = null) = show(ConfirmationActivity.OPEN_ON_PHONE_ANIMATION, message)
}
