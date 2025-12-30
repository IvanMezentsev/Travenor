package com.example.travenor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// Клас для подій навігації
sealed class SplashDestination {
    object MainActivity : SplashDestination()
    object SignInActivity : SplashDestination()
    object OnboardingActivity : SplashDestination()
}

class SplashViewModel : ViewModel() {

    private val _destination = MutableLiveData<SplashDestination>()
    val destination: LiveData<SplashDestination> = _destination

    fun checkUserStatus(isFirstTime: Boolean) {
        viewModelScope.launch {
            // Чекаємо 2 секунди (бізнес-логіка затримки)
            delay(2000)

            val user = FirebaseAuth.getInstance().currentUser

            if (user != null) {
                _destination.value = SplashDestination.MainActivity
            } else if (isFirstTime) {
                _destination.value = SplashDestination.OnboardingActivity
            } else {
                _destination.value = SplashDestination.SignInActivity
            }
        }
    }
}