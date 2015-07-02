package io.wake.wear.ui.activity

import io.wake.wear.presenter.Presenter
import kotlin.properties.Delegates

abstract class PresenterActivity<PRESENTER : Presenter<*>>(layout: Int) : BaseActivity(layout) {
    protected var presenter: PRESENTER by Delegates.notNull()

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun onPause() {
        super.onPause()
        presenter.onPause()
    }
}