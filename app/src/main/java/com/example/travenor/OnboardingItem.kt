package com.example.travenor

data class OnboardingItem(
    val image: Int,
    val fullText: String,      // Весь текст
    val highlightText: String, // Слово для підкреслення
    val description: String
)