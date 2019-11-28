package com.dvpermyakov.exchangerate.android

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dvpermyakov.exchangerate.presentation.RateListState

class RateListAdapter(
    private val listener: RateItemViewHolder.RateListListener
) : ListAdapter<RateListState.RateItem, RecyclerView.ViewHolder>(DiffItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RateItemViewHolder(parent, listener)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is RateItemViewHolder -> {
                holder.bind(getItem(position))
            }
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
    }
}
