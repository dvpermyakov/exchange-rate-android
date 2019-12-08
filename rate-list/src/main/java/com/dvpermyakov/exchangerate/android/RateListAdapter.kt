package com.dvpermyakov.exchangerate.android

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dvpermyakov.exchangerate.presentation.RateListState

class RateListAdapter(
    private val listener: Listener
) : ListAdapter<RateListState.RateItem, RecyclerView.ViewHolder>(DiffItemCallback()) {

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            VIEW_TYPE_EDITABLE
        } else {
            VIEW_TYPE_NOT_EDITABLE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_EDITABLE -> {
                RateItemEditableViewHolder(parent, listener)
            }
            VIEW_TYPE_NOT_EDITABLE -> {
                RateItemViewHolder(parent, listener)
            }
            else -> {
                RateItemViewHolder(parent, listener)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is RateItemViewHolder -> {
                holder.bind(getItem(position))
            }
            is RateItemEditableViewHolder -> {
                holder.bind(getItem(position))
            }
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        (payloads.firstOrNull() as? DiffItemCallback.Payload)?.let { payload ->
            when (holder) {
                is RateItemViewHolder -> {
                    holder.setValue(payload.value)
                }
                is RateItemEditableViewHolder -> {
                }
            }
        } ?: run {
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    private class DiffItemCallback : DiffUtil.ItemCallback<RateListState.RateItem>() {

        override fun areItemsTheSame(
            oldItem: RateListState.RateItem,
            newItem: RateListState.RateItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: RateListState.RateItem,
            newItem: RateListState.RateItem
        ): Boolean {
            return oldItem == newItem
        }

        override fun getChangePayload(
            oldItem: RateListState.RateItem,
            newItem: RateListState.RateItem
        ): Any? {
            return if (oldItem.code == newItem.code && oldItem.name == newItem.name && oldItem.image == newItem.image) {
                Payload(newItem.value)
            } else {
                null
            }
        }

        data class Payload(
            val value: String
        )
    }

    interface Listener :
        RateItemViewHolder.RateListListener,
        RateItemEditableViewHolder.EditableRateListListener

    companion object {
        private const val VIEW_TYPE_EDITABLE = 1
        private const val VIEW_TYPE_NOT_EDITABLE = 2
    }
}
