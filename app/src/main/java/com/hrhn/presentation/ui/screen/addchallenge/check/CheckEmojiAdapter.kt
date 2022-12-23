package com.hrhn.presentation.ui.screen.addchallenge.check

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hrhn.databinding.ItemCheckableEmojiBinding
import com.hrhn.presentation.ui.screen.addchallenge.Emoji

class CheckEmojiAdapter(
    private val onCheckedChange: (Emoji) -> Unit
) : ListAdapter<Emoji, CheckEmojiAdapter.ViewHolder>(diffUtil) {
    class ViewHolder(
        private val binding: ItemCheckableEmojiBinding,
        private val onCheckedChange: (Emoji) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(emoji: Emoji) {
            binding.emoji = emoji
            binding.ivEmoji.setBackgroundColor(Color.parseColor(emoji.color))
            binding.root.setOnClickListener { onCheckedChange(emoji) }
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
        val diffUtil = object : ItemCallback<Emoji>() {
            override fun areItemsTheSame(oldItem: Emoji, newItem: Emoji): Boolean {
                return oldItem.label == newItem.label
            }

            override fun areContentsTheSame(oldItem: Emoji, newItem: Emoji): Boolean {
                return oldItem.isChecked == newItem.isChecked
            }
        }
    }
}