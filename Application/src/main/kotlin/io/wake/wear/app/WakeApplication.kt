package io.wake.wear.app

import android.content.Context

public class WakeApplication : BaseApplication() {
    override fun onCreate() {
        super<BaseApplication>.onCreate()
        component.inject(this)
    }

    companion object {
        fun application(context: Context): WakeApplication
                = context.getApplicationContext() as WakeApplication

        fun component(context: Context): ApplicationComponent = application(context).component
    }
}