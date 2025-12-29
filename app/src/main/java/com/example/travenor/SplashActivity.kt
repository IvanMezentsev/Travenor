package com.example.travenor

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()

        Handler(Looper.getMainLooper()).postDelayed({
            val user = FirebaseAuth.getInstance().currentUser
            // Перевіряємо Shared Preferences
            val sharedPref = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
            val isFirstTime = !sharedPref.getBoolean("FinishedOnboarding", false) // Якщо false (немає запису), значить це перший раз (true)

            if (user != null) {
                // 1. Якщо користувач залогінений -> Main
                startActivity(Intent(this, MainActivity::class.java))
            } else if (isFirstTime) {
                // 2. Якщо не залогінений і перший запуск -> Onboarding
                startActivity(Intent(this, OnboardingActivity::class.java))
            } else {
                // 3. Якщо не залогінений, але вже бачив Onboarding -> Sign In
                startActivity(Intent(this, SignInActivity::class.java))
            }
            finish()
        }, 2000)
    }
}