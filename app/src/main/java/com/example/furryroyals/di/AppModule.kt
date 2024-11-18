package com.example.furryroyals.di

import android.content.Context
import com.example.furryroyals.auth.data.networking.AuthApi

import com.example.furryroyals.auth.presentation.registration.RegistrationViewModel
import com.example.furryroyals.core.AuthViewModel
import com.example.furryroyals.core.data.networking.HttpClientFactory
import com.example.furryroyals.repository.UserRepository
import com.example.furryroyals.ui.profile.ProfileViewModel
import com.example.furryroyals.ui.profile.accountDetail.AccountDetailViewModel
import io.ktor.client.engine.cio.CIO
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { HttpClientFactory.create(CIO.create()) }
    single { androidContext().getSharedPreferences("app_prefs", Context.MODE_PRIVATE) }
    single { AuthApi(get()) }
    single { UserRepository(get()) }

    viewModel { AuthViewModel(get()) }
    viewModel { ProfileViewModel(get()) }
    viewModel { RegistrationViewModel(get(), get()) }
    viewModel { AccountDetailViewModel(get(), get()) }
}
