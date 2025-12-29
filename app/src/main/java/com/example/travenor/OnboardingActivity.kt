package com.example.travenor

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2

class OnboardingActivity : AppCompatActivity() {

    private lateinit var onboardingAdapter: OnboardingAdapter
    private lateinit var layoutIndicators: LinearLayout
    private lateinit var btnOnboardingAction: AppCompatButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        // Прибираємо верхню панель
        supportActionBar?.hide()

        layoutIndicators = findViewById(R.id.layoutIndicators)
        btnOnboardingAction = findViewById(R.id.btnOnboardingAction)
        val tvSkip = findViewById<TextView>(R.id.tvSkip)
        val viewPager = findViewById<ViewPager2>(R.id.viewPager)

        setupOnboardingItems(viewPager)
        setupIndicators()
        setCurrentIndicator(0)

        // Слухач перегортання сторінок
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)
                if (position == 0) {
                    btnOnboardingAction.text = getString(R.string.onboarding_button_name_1)
                } else {
                    btnOnboardingAction.text = getString(R.string.onboarding_button_name_2)
                }
            }
        })

        // Логіка головної кнопки
        btnOnboardingAction.setOnClickListener {
            val currentItem = viewPager.currentItem
            // Якщо це не останній слайд - гортаємо далі
            if (currentItem + 1 < onboardingAdapter.itemCount) {
                viewPager.currentItem = currentItem + 1
            } else {
                // Якщо останній слайд - завершуємо
                finishOnboarding()
            }
        }

        // Логіка кнопки Skip
        tvSkip.setOnClickListener {
            finishOnboarding()
        }
    }

    private fun setupOnboardingItems(viewPager: ViewPager2) {
        val onboardingItems = listOf(
            OnboardingItem(
                R.drawable.onboard1,
                fullText = getString(R.string.onboarding_title_1),
                highlightText = "wide", // Це слово стане помаранчевим і отримає дужку
                description = getString(R.string.onboarding_description_1)
            ),
            OnboardingItem(
                R.drawable.onboard2,
                fullText = getString(R.string.onboarding_title_2),
                highlightText = "explore",
                description = getString(R.string.onboarding_description_2)
            ),
            OnboardingItem(
                R.drawable.onboard3,
                fullText = getString(R.string.onboarding_title_3),
                highlightText = "people",
                description = getString(R.string.onboarding_description_3)
            )
        )
        onboardingAdapter = OnboardingAdapter(onboardingItems)
        viewPager.adapter = onboardingAdapter
    }

    // Малюємо крапочки
    private fun setupIndicators() {
        val indicators = arrayOfNulls<ImageView>(onboardingAdapter.itemCount)
        val layoutParams: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        layoutParams.setMargins(8, 0, 8, 0)

        for (i in indicators.indices) {
            indicators[i] = ImageView(applicationContext)
            indicators[i]?.let {
                it.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.indicator_inactive))
                it.layoutParams = layoutParams
                layoutIndicators.addView(it)
            }
        }
    }

    // Змінюємо вигляд активної крапочки
    private fun setCurrentIndicator(index: Int) {
        val childCount = layoutIndicators.childCount
        for (i in 0 until childCount) {
            val imageView = layoutIndicators.getChildAt(i) as ImageView
            if (i == index) {
                imageView.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.indicator_active))
            } else {
                imageView.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.indicator_inactive))
            }
        }
    }

    private fun finishOnboarding() {
        // Зберігаємо в пам'ять телефону, що Onboarding пройдено
        val sharedPref = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("FinishedOnboarding", true)
        editor.apply()

        // Переходимо на екран входу
        startActivity(Intent(this, SignInActivity::class.java))
        finish()
    }
}