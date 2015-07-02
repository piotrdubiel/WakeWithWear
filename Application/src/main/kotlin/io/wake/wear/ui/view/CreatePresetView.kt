package io.wake.wear.ui.view

import rx.Observable
import rx.android.view.OnClickEvent
import rx.android.widget.OnTextChangeEvent

public trait CreatePresetView : PresentationView {
    fun observeMacField(emit: Boolean = false): Observable<OnTextChangeEvent>
    fun observeAddress(emit: Boolean = false): Observable<OnTextChangeEvent>
    fun observeSave(): Observable<OnClickEvent>
    fun setMacError(error: String)
    fun setAddressError(error: String)
    fun saveEnabled(valid: Boolean)
    fun done()
}