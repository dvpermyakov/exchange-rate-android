package com.dvpermyakov.exchangerate.android

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dvpermyakov.exchangerate.R
import com.dvpermyakov.exchangerate.presentation.RateListState
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_rate.*

class RateItemEditableViewHolder(
    parent: ViewGroup,
    private val listener: EditableRateListListener
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(
        R.layout.item_rate_editable,
        parent,
        false
    )
), LayoutContainer {

    override val containerView: View? = itemView

    fun bind(item: RateListState.RateItem) {
        if (item.image.isNotBlank()) {
            Picasso.get()
                .load(item.image)
                .into(imageView)
        }
        codeView.text = item.code
        nameView.text = item.name

        valueEditTextView.setText(item.value)
        valueEditTextView.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                listener.onValueChange(p0!!.toString())
            }
        })
    }

    interface EditableRateListListener {
        fun onValueChange(value: String)
    }
}
