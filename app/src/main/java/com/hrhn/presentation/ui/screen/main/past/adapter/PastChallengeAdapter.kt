package com.hrhn.presentation.ui.screen.main.past.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.RecyclerView
import com.hrhn.databinding.ItemChallengeBinding
import com.hrhn.domain.model.Challenge
import com.hrhn.presentation.util.Color.NONE

class PastChallengeAdapter(
    private val review: (Challenge) -> Unit
) : PagingDataAdapter<Challenge, PastChallengeAdapter.ViewHolder>(diffUtil) {

    class ViewHolder(
        private val binding: ItemChallengeBinding,
        private val review: (Challenge) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(challenge: Challenge) {
            with(binding) {
                this.challenge = challenge
                ivEmoji.setBackgroundColor(Color.parseColor(challenge.emoji?.color ?: NONE))
            }
            binding.root.setOnClickListener { review(challenge) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemChallengeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), review
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    companion object {
        val diffUtil = object : ItemCallback<Challenge>() {
            override fun areItemsTheSame(oldItem: Challenge, newItem: Challenge): Boolean {
                return oldItem.date == newItem.date
            }

            override fun areContentsTheSame(oldItem: Challenge, newItem: Challenge): Boolean {
                return oldItem == newItem
            }
        }
    }
}