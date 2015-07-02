package io.wake.wear.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.bindView
import com.squareup.picasso.Picasso
import io.wake.wear.R
import io.wake.wear.common.model.Preset
import io.wake.wear.getStaticMapUrl
import io.wake.wear.ui.view.singleClick
import java.util.LinkedList

public class PresetsAdapter : RecyclerView.Adapter<PresetsAdapter.ViewHolder>() {

    inner class ViewHolder(val view: View, var action: ((Preset) -> Unit)? = {}) : RecyclerView.ViewHolder(view) {
        val actionTextView: TextView by bindView(R.id.preset_tv_action)
        val placeTextView: TextView by bindView(R.id.preset_tv_place)
        val lastTriggerTextView: TextView by bindView(R.id.preset_tv_last_trigger)
        val mapImageView: ImageView by bindView(R.id.preset_iv_map)

        fun setItem(item: Preset) {
            itemView?.singleClick { action?.invoke(item) }

            actionTextView.setText(item.getName())
            placeTextView.setText("${item.getLatitude()}, ${item.getLongitude()}")
            lastTriggerTextView.setText("5 minutes ago ")
            Picasso.with(itemView.getContext())
                    .load(getStaticMapUrl(52.225026, 21.007987))
                    .into(mapImageView)
        }
    }

    var presets: List<Preset> = LinkedList()
        set(value: List<Preset>) {
            $presets = value
            notifyDataSetChanged()
        }

    var onItemClickListener: ((Preset) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder? {
        val view = LayoutInflater.from(parent!!.getContext()).inflate(R.layout.item_preset, parent, false)
        return ViewHolder(view, onItemClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.setItem(presets[position])
    }

    override fun getItemCount(): Int = presets.size()
}