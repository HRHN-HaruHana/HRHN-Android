package com.hrhn.presentation.ui.screen.storage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hrhn.databinding.ItemStorageBinding
import com.hrhn.domain.model.StorageItem

class StorageAdapter(
    private val onClick: (StorageItem) -> Unit,
    private val onDelete: (StorageItem) -> Unit
) : ListAdapter<StorageItem, StorageAdapter.ViewHolder>(diffUtil) {

    class ViewHolder(
        private val binding: ItemStorageBinding,
        private val onClick: (StorageItem) -> Unit,
        private val onDelete: (StorageItem) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: StorageItem) {
            binding.tvContent.text = item.content
            binding.root.setOnClickListener { onClick(item) }
            binding.ibtDelete.setOnClickListener { onDelete(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemStorageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), onClick, onDelete
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val diffUtil = object : ItemCallback<StorageItem>() {
            override fun areItemsTheSame(oldItem: StorageItem, newItem: StorageItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: StorageItem, newItem: StorageItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}