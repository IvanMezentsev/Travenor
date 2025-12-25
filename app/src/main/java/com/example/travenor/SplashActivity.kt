package com.example.travenor

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
            // Перевіряємо, чи користувач вже увійшов
            val user = FirebaseAuth.getInstance().currentUser

            if (user != null) {
                // Якщо користувач є -> йдемо на Головну
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                // Якщо ні -> на екран Входу
                startActivity(Intent(this, SignInActivity::class.java))
            }

            finish() // Закриваємо Splash
        }, 2000)
    }
}