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
import kotlinx.android.synthetic.main.item_rate_editable.*

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

    init {
        focusableEditTextView.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                listener.onValueChange(s.toString())
            }
        })
    }

    fun bind(item: RateListState.RateItem) {
        if (item.image.isNotBlank()) {
            Picasso.get()
                .load(item.image)
                .into(imageView)
        }
        codeView.text = item.code
        nameView.text = item.name

        focusableEditTextView.setText(item.value)
        focusableEditTextView.setSelection(item.value.length)
    }

    interface EditableRateListListener {
        fun onValueChange(value: String)
    }
}
