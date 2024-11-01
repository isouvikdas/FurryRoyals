package com.example.furryroyals.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.furryroyals.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProfileUiState(
    val phoneNumber: String = "",
    val logOutSuccess: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _profileUiState = MutableStateFlow(ProfileUiState())
    val profileUiState = _profileUiState.asStateFlow()

    init {
        setUserPhoneNumber()
    }

    private fun setUserPhoneNumber() {
        viewModelScope.launch {
            val phoneNumber = userRepository.getUserPhoneNumber()
            _profileUiState.update {
                it.copy(
                    phoneNumber = phoneNumber!!
                )
            }
        }
    }

}