package com.example.furryroyals.ui.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.furryroyals.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class ProfileViewModel (
    private val userRepository: UserRepository
) : ViewModel() {

    private var _phoneNumber = MutableStateFlow("")
    val phoneNumber = _phoneNumber.asStateFlow()

    init {
        setUserPhoneNumber()
    }

    private fun setUserPhoneNumber() {
        viewModelScope.launch {
            val number = userRepository.getUserPhoneNumber()
            number?.let {
                Log.e("Profile", number)
            }
            Log.e("Profile", "")
            number?.let {
                _phoneNumber.value = number
            }
        }
    }
}

