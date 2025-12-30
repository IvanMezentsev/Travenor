package com.example.travenor

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

class SplashActivity : AppCompatActivity() {

    private lateinit var viewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()

        // Ініціалізація ViewModel
        viewModel = ViewModelProvider(this)[SplashViewModel::class.java]

        // Отримуємо дані про перший запуск
        val sharedPref = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        val isFirstTime = !sharedPref.getBoolean("FinishedOnboarding", false)

        // Запускаємо перевірку
        viewModel.checkUserStatus(isFirstTime)

        // Підписуємося на результат (Observer)
        viewModel.destination.observe(this) { destination ->
            when (destination) {
                is SplashDestination.MainActivity -> {
                    startActivity(Intent(this, MainActivity::class.java))
                }
                is SplashDestination.SignInActivity -> {
                    startActivity(Intent(this, SignInActivity::class.java))
                }
                is SplashDestination.OnboardingActivity -> {
                    startActivity(Intent(this, OnboardingActivity::class.java))
                }
            }
            finish()
        }
    }
}