package io.wake.wear.ui.activity

import android.os.Bundle
import android.support.v4.app.ActionBarDrawerToggle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import butterknife.bindView
import com.melnykov.fab.FloatingActionButton
import io.wake.wear.R
import io.wake.wear.app.WakeApplication
import io.wake.wear.common.model.Preset
import io.wake.wear.presenter.PresetsPresenter
import io.wake.wear.store.PresetStore
import io.wake.wear.ui.activity.create.CreatePresetActivity
import io.wake.wear.ui.activity.create.PickAddressActivity
import io.wake.wear.ui.adapter.PresetsAdapter
import io.wake.wear.ui.view.PresetsView
import io.wake.wear.ui.view.singleClick
import rx.android.view.ViewObservable
import javax.inject.Inject
import kotlin.properties.Delegates

public class PresetsActivity : PresenterActivity<PresetsPresenter>(R.layout.activity_preset_list), PresetsView {
    private var TAG = javaClass.getSimpleName()

    val adapter = PresetsAdapter()
    val presetsRecyclerView: RecyclerView by bindView(R.id.preset_list)
    val createButton: FloatingActionButton by bindView(R.id.btn_presets_add)

    var store: PresetStore? = null
        [Inject] set

    override fun onCreate(savedInstanceState: Bundle?) {
        super<PresenterActivity>.onCreate(savedInstanceState)
        WakeApplication.component(this).inject(this)
        init()
    }

    override fun showPresets(presets: List<Preset>) {
        adapter.presets = presets
    }

    override fun navigateToPreset(id: Long) {
        startActivity(DetailActivity.intent(this, id))
    }

    override fun navigateToCreate() {
        startActivity(PickAddressActivity.intent(this))
    }

    private fun init() {
        presenter = PresetsPresenter(this, store!!)

        val layoutManager = LinearLayoutManager(this)
        presetsRecyclerView.setLayoutManager(layoutManager)
        presetsRecyclerView.setHasFixedSize(true)
        presetsRecyclerView.setAdapter(adapter)
        adapter.onItemClickListener = { presenter.onPresetClicked(it) }
        createButton.singleClick { presenter.onCreateClicked() }
    }
}