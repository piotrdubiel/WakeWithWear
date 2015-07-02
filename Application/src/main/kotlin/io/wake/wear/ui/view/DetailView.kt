package io.wake.wear.ui.view

import rx.Observable
import rx.android.view.OnClickEvent

public trait DetailView : PresentationView {
    fun showDeleteConfirmation()
    fun done()
}