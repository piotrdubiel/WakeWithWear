package io.wake.wear.ui.activity

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import butterknife.bindView
import com.squareup.picasso.Picasso
import io.wake.wear.R
import io.wake.wear.app.WakeApplication
import io.wake.wear.presenter.DetailPresenter
import io.wake.wear.store.PresetStore
import io.wake.wear.ui.view.DetailView
import rx.Observable
import rx.android.view.OnClickEvent
import rx.android.view.ViewObservable
import javax.inject.Inject

public class DetailActivity : PresenterActivity<DetailPresenter>(R.layout.activity_preset_detail), DetailView {
    var store: PresetStore? = null
        [Inject] set

    val map: ImageView by bindView(R.id.iv_detail_map)

    companion object {
        fun intent(ctx: Context, id: Long): Intent
                = Intent(ctx, javaClass<DetailActivity>()).putExtra("id", id)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super<PresenterActivity>.onCreate(savedInstanceState)
        WakeApplication.component(this).inject(this)
        init()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        getMenuInflater().inflate(R.menu.menu_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.getItemId()) {
            android.R.id.home -> {
                finish()
                return true
            }
            R.id.btn_detail_delete -> showDeleteConfirmation()
        }
        return super<PresenterActivity>.onOptionsItemSelected(item)
    }

    private fun init() {
        val id = getIntent().getLongExtra("id", -1)
        presenter = DetailPresenter(this, store!!)
        presenter.init(id)
        setTitle(null)
        getSupportActionBar().setDisplayHomeAsUpEnabled(true)
        Picasso.with(this).load(presenter.getMapUrl()).into(map)
    }

    override fun showDeleteConfirmation() {
        AlertDialog.Builder(this)
                .setTitle("Delete preset?")
                .setMessage("Do you want to delete this preset?")
                .setPositiveButton("Delete", { dialog, button -> presenter.performDelete() })
                .setNegativeButton("Cancel", null)
                .show()
    }

    override fun done() {
        finish()
    }
}
