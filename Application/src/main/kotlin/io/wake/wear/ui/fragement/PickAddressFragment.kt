package io.wake.wear.ui.fragement

import android.os.Bundle
import android.support.v4.app.Fragment
import io.wake.wear.app.WakeApplication
import io.wake.wear.presenter.CreatePresetPresenter
import javax.inject.Inject

public class PickAddressFragment : Fragment() {
    var presenter: CreatePresetPresenter? = null
        [Inject] set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WakeApplication.component(getActivity()).inject(this)
    }
}