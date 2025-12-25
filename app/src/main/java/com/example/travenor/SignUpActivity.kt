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
import com.google.firebase.auth.UserProfileChangeRequest

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        auth = FirebaseAuth.getInstance()

        val etName = findViewById<EditText>(R.id.etName)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.etPassword)
        val btnSignUp = findViewById<AppCompatButton>(R.id.btnSignUp)
        val tvSignInLink = findViewById<TextView>(R.id.tvSignInLink)
        val btnBack = findViewById<android.view.View>(R.id.btnBack) // Кнопка назад

        // 1. Кнопка "Назад"
        btnBack.setOnClickListener { finish() }

        // 2. Логіка реєстрації
        btnSignUp.setOnClickListener {
            val name = etName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password.length < 8) {
                Toast.makeText(this, "Password must be at least 8 chars", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Створення акаунту
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Оновлюємо профіль, щоб зберегти Ім'я користувача
                        val user = auth.currentUser
                        val profileUpdates = UserProfileChangeRequest.Builder()
                            .setDisplayName(name)
                            .build()

                        user?.updateProfile(profileUpdates)?.addOnCompleteListener {
                            // Після успішного збереження імені переходимо далі
                            Toast.makeText(this, "Account created!", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, MainActivity::class.java))
                            finishAffinity() // Закриває всі попередні екрани (Sign Up, Sign In)
                        }
                    } else {
                        Toast.makeText(this, "Error: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                    }
                }
        }

        // 3. Перехід на екран входу
        tvSignInLink.setOnClickListener {
            finish() // Просто повертаємось назад на SignInActivity
        }

        // 4. Логіка соцмереж
        setupSocialIcons()
    }

    private fun setupSocialIcons() {
        val instagram = findViewById<ImageView>(R.id.instagram_logo)
        val facebook = findViewById<ImageView>(R.id.facebook_logo)
        val twitter = findViewById<ImageView>(R.id.twitter_logo)

        val socialListener = { view: android.view.View ->
            Toast.makeText(this, "Coming soon", Toast.LENGTH_SHORT).show()
            view.isEnabled = false
            view.alpha = 0.5f
        }

        instagram.setOnClickListener(socialListener)
        facebook.setOnClickListener(socialListener)
        twitter.setOnClickListener(socialListener)
    }
}