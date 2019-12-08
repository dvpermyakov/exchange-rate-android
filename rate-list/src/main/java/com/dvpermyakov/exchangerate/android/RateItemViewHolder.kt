package com.dvpermyakov.exchangerate.android

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dvpermyakov.exchangerate.R
import com.dvpermyakov.exchangerate.presentation.RateListState
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_rate.*

class RateItemViewHolder(
    parent: ViewGroup,
    private val listener: RateListListener
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(
        R.layout.item_rate,
        parent,
        false
    )
), LayoutContainer {

    override val containerView: View? = itemView

    fun bind(item: RateListState.RateItem) {
        containerViewGroup.setOnClickListener {
            listener.onRateItemClick(item.id, valueEditTextView.text.toString())
        }
        if (item.image.isNotBlank()) {
            Picasso.get()
                .load(item.image)
                .into(imageView)
        }
        codeView.text = item.code
        nameView.text = item.name

        valueEditTextView.setText(item.value)
    }

    fun setValue(value: String) {
        valueEditTextView.setText(value)
    }

    interface RateListListener {
        fun onRateItemClick(rateId: String, value: String)
    }
}
