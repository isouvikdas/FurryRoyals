package com.example.furryroyals.ui.profile

import androidx.lifecycle.ViewModel
import com.example.furryroyals.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject



@HiltViewModel
class ProfileViewModel @Inject constructor(
    userRepository: UserRepository
) : ViewModel() {

}