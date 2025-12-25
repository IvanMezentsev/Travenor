package com.example.travenor

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.google.firebase.auth.FirebaseAuth

class SignInActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        // Ініціалізація Firebase
        auth = FirebaseAuth.getInstance()

        // Знаходимо елементи
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.etPassword)
        val btnSignIn = findViewById<AppCompatButton>(R.id.btnSignIn)
        val tvSignUpLink = findViewById<TextView>(R.id.tvSignUpLink)
        val tvForgotPassword = findViewById<TextView>(R.id.tvForgotPassword)

        // 1. Логіка кнопки входу
        btnSignIn.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Вхід через Firebase
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Успішний вхід -> MainActivity
                        Toast.makeText(this, "Welcome back!", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    } else {
                        // Помилка
                        Toast.makeText(this, "Error: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                    }
                }
        }

        // 2. Перехід на екран реєстрації
        tvSignUpLink.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
        // 3. Кнопка забули пароль
        tvForgotPassword.setOnClickListener {
            Toast.makeText(this, "Not yet implemented", Toast.LENGTH_SHORT).show()
        }

        // 4. Логіка соцмереж (Instagram, Facebook, Twitter)
        setupSocialIcons()
    }

    private fun setupSocialIcons() {
        val instagram = findViewById<ImageView>(R.id.instagram_logo)
        val facebook = findViewById<ImageView>(R.id.facebook_logo)
        val twitter = findViewById<ImageView>(R.id.twitter_logo)

        val socialListener = { view: android.view.View ->
            Toast.makeText(this, "Coming soon", Toast.LENGTH_SHORT).show()
            view.isEnabled = false // Робимо неактивним
            view.alpha = 0.5f      // Робимо напівпрозорим (візуально неактивним)
        }

        instagram.setOnClickListener(socialListener)
        facebook.setOnClickListener(socialListener)
        twitter.setOnClickListener(socialListener)
    }
}