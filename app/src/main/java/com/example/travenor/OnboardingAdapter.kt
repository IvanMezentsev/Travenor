package com.example.travenor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.text.Spannable
import android.text.SpannableString
import androidx.recyclerview.widget.RecyclerView


// Адаптер для ViewPager2
class OnboardingAdapter(private val onboardingItems: List<OnboardingItem>) :
    RecyclerView.Adapter<OnboardingAdapter.OnboardingViewHolder>() {

    inner class OnboardingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imageOnboarding = view.findViewById<ImageView>(R.id.imageOnboarding)
        private val textTitle = view.findViewById<TextView>(R.id.textTitle)
        private val textDescription = view.findViewById<TextView>(R.id.textDescription)

        fun bind(item: OnboardingItem) {
            imageOnboarding.setImageResource(item.image)
            textDescription.text = item.description

            val spannable = SpannableString(item.fullText)

            // Шукаємо, де починається слово, яке треба виділити
            val startIndex = item.fullText.indexOf(item.highlightText)

            if (startIndex >= 0) {
                val endIndex = startIndex + item.highlightText.length

                // Накладаємо наш Span (колір + дужка)
                spannable.setSpan(
                    CurveLineSpan(itemView.context, R.color.travenor_orange), // Передаємо контекст і колір
                    startIndex,
                    endIndex,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }

            textTitle.text = spannable
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardingViewHolder {
        return OnboardingViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_onboarding, // Цей файл створимо наступним
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: OnboardingViewHolder, position: Int) {
        holder.bind(onboardingItems[position])
    }

    override fun getItemCount(): Int {
        return onboardingItems.size
    }
}