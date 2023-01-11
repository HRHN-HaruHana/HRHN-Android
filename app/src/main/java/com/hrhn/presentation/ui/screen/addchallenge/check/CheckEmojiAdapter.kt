package com.hrhn.presentation.ui.screen.addchallenge.check

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hrhn.databinding.ItemCheckableEmojiBinding
import com.hrhn.presentation.ui.screen.addchallenge.CheckableEmoji

class CheckEmojiAdapter(
    private val onCheckedChange: (CheckableEmoji) -> Unit
) : ListAdapter<CheckableEmoji, CheckEmojiAdapter.ViewHolder>(diffUtil) {
    class ViewHolder(
        private val binding: ItemCheckableEmojiBinding,
        private val onCheckedChange: (CheckableEmoji) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CheckableEmoji) {
            binding.info = item
            binding.ivEmoji.setBackgroundColor(Color.parseColor(item.emoji.color))
            binding.root.setOnClickListener { onCheckedChange(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemCheckableEmojiBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onCheckedChange
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val diffUtil = object : ItemCallback<CheckableEmoji>() {
            override fun areItemsTheSame(
                oldItem: CheckableEmoji, newItem: CheckableEmoji
            ): Boolean {
                return oldItem.emoji.label == newItem.emoji.label
            }

            override fun areContentsTheSame(
                oldItem: CheckableEmoji, newItem: CheckableEmoji
            ): Boolean {
                return oldItem.isChecked == newItem.isChecked
            }
        }
    }
}