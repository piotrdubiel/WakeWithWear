package io.wake.wear.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import butterknife.bindView
import io.wake.wear.R
import io.wake.wear.presenter.Presenter
import kotlin.properties.Delegates

abstract class BaseActivity(val layout: Int) : AppCompatActivity() {
    protected val toolbar: Toolbar by bindView(R.id.toolbar)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout)
        setSupportActionBar(toolbar)
    }
}