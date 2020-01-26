package com.tno.cuhackit2020.view

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.tno.cuhackit2020.R
import com.tno.cuhackit2020.databinding.ViewLikeCounterBinding

class LikeCounter @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    var binding: ViewLikeCounterBinding

    var count: String
        get() = binding.count.text.toString()
        set(value) {
            binding.count.text = value
        }

    init {
        inflate(context, R.layout.view_like_counter, this)
        binding = ViewLikeCounterBinding.bind(findViewById(R.id.counterRoot))

        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.LikeCounter,
            0, 0
        ).apply {

            try {
                count = getString(R.styleable.LikeCounter_count) ?: ""
            } finally {
                recycle()
            }
        }
    }
}