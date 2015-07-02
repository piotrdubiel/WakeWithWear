package io.wake.wear.app

import android.content.Context

public class WearApplication : BaseApplication() {
    override fun onCreate() {
        super<BaseApplication>.onCreate()
        component.inject(this)
    }

    companion object {
        fun component(context: Context): ApplicationComponent {
            return (context.getApplicationContext() as WearApplication).component
        }
    }
}