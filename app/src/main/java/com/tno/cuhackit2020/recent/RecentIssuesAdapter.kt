package com.tno.cuhackit2020.recent

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tno.cuhackit2020.R
import com.tno.cuhackit2020.databinding.ItemIssueBinding
import com.tno.cuhackit2020.model.RecentIssuesWrapper

class RecentIssuesAdapter :
    ListAdapter<RecentIssuesWrapper, RecentIssuesAdapter.RecentIssuesVH>(DIFF) {

    var onLikeButtonClick: (liked: Boolean?, item: RecentIssuesWrapper) -> Unit = { _, _ -> }
    var onThingSelected: (item: RecentIssuesWrapper) -> Unit = {}

    companion object {
        val DIFF = object : DiffUtil.ItemCallback<RecentIssuesWrapper>() {
            override fun areItemsTheSame(
                oldItem: RecentIssuesWrapper,
                newItem: RecentIssuesWrapper
            ): Boolean {
                return (oldItem is RecentIssuesWrapper.RecentIssue &&
                        newItem is RecentIssuesWrapper.RecentIssue &&
                        oldItem.issue.issueid == newItem.issue.issueid) ||
                        (oldItem is RecentIssuesWrapper.RecentSuggestion &&
                                newItem is RecentIssuesWrapper.RecentSuggestion &&
                                oldItem.suggestion.suggestionsid == newItem.suggestion.suggestionsid)
            }

            override fun areContentsTheSame(
                oldItem: RecentIssuesWrapper,
                newItem: RecentIssuesWrapper
            ): Boolean {
                return if (oldItem is RecentIssuesWrapper.RecentIssue && newItem is RecentIssuesWrapper.RecentIssue) {
                    oldItem.issue == newItem.issue
                } else if (oldItem is RecentIssuesWrapper.RecentSuggestion && newItem is RecentIssuesWrapper.RecentSuggestion) {
                    oldItem.suggestion.suggestionsid == newItem.suggestion.suggestionsid
                } else {
                    false
                }
            }

        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is RecentIssuesWrapper.RecentIssue -> 1
            is RecentIssuesWrapper.RecentSuggestion -> 2
            else -> 1
        }
    }


    abstract inner class RecentIssuesVH(val root: ItemIssueBinding) :
        RecyclerView.ViewHolder(root.root) {
        abstract fun bind(item: RecentIssuesWrapper.RecentIssue)
        abstract fun bind(item: RecentIssuesWrapper.RecentSuggestion)

        val context = root.root.context

        fun formatDateLocation(location: String, date: String) {
            root.locationDate.text =
                root.root.context.getString(R.string.location_date, location, date)
        }

        fun setupLikeBtn(item: RecentIssuesWrapper) {
            val liked = when (item) {
                is RecentIssuesWrapper.RecentIssue -> {
                    item.issue.liked
                }
                is RecentIssuesWrapper.RecentSuggestion -> {
                    item.suggestion.liked
                }
            }

            val newColor = when (liked) {
                true -> R.color.positive
                false -> R.color.negative
                null -> R.color.black
            }

            root.fakeLikeBtn.setCardBackgroundColor(root.root.context.getColor(newColor))

            root.likeBtn.setOnClickListener {
                if (liked != true) {
                    transition(item, true)
                    onLikeButtonClick(true, item)
                }
            }

            root.dislikeBtn.setOnClickListener {
                if (liked != false) {
                    transition(item, false)
                    onLikeButtonClick(false, item)
                }
            }
            root.fakeLikeBtn.setOnClickListener {
                item
            }


            root.fakeLikeBtn.setOnLongClickListener {
                transition(item, null)
                onLikeButtonClick(null, item)
                true
            }
        }

        private fun transition(item: RecentIssuesWrapper, liked: Boolean?) {
            val currentColor = root.fakeBtnText.backgroundTintList?.defaultColor ?: 0

            val newColor = when (liked) {
                true -> R.color.positive
                false -> R.color.negative
                null -> R.color.black
            }
            val endColor = root.root.context.getColor(newColor)

            val transition = when (liked) {
                true -> R.id.liked
                false -> R.id.disliked
                null -> R.id.neutral
            }

            root.likedMotionLayout.setTransitionDuration(300)
            root.likedMotionLayout.transitionToState(transition)

        }
    }

    inner class IssueVH(private val binding: ItemIssueBinding) : RecentIssuesVH(binding) {
        override fun bind(item: RecentIssuesWrapper.RecentIssue) {
            val issue = item.issue

            binding.root.setOnClickListener { onThingSelected(item) }

            formatDateLocation(item.area, issue.date)
            setupLikeBtn(item)

            with(binding) {
                bottomShadow.setBackgroundColor(context.getColor(R.color.issue))
                replyImg.isVisible = false
                binding.fakeBtnText.text = issue.likes.toString() + " likes"
                description.text = issue.description
                title.text = issue.title
                username.text = item.author.fullName
            }
        }

        override fun bind(item: RecentIssuesWrapper.RecentSuggestion) {
            val suggestion = item.suggestion

            onThingSelected(item)
            formatDateLocation(item.area, suggestion.date)
            setupLikeBtn(item)

            with(binding) {
                bottomShadow.setBackgroundColor(context.getColor(R.color.suggestion))

                binding.fakeBtnText.text = suggestion.likes.toString() + " likes"
                binding.title.text = when {
                    item.parent != null -> {
                        binding.title.isVisible = true
                        binding.description.isVisible = true
                        item.parent.description

                    }
                    item.issue != null -> {
                        binding.title.isVisible = true
                        binding.description.isVisible = true
                        binding.description.text = item.suggestion.description
                        item.suggestion.description

                    }
                    else -> {
                        binding.description.isVisible = false
                        item.suggestion.description
                    }
                }

                replyImg.isVisible = item.parent != null
                description.text = suggestion.description
                username.text = item.author.fullName
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentIssuesVH {
        val inflater = LayoutInflater.from(parent.context)
        return IssueVH(ItemIssueBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: RecentIssuesVH, position: Int) {
        when (val item = getItem(position)) {
            is RecentIssuesWrapper.RecentIssue -> holder.bind(item)
            is RecentIssuesWrapper.RecentSuggestion -> holder.bind(item)
        }
    }
}