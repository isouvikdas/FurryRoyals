package com.example.furryroyals.di

import android.content.Context
import androidx.room.Room
import com.example.furryroyals.auth.data.networking.AuthApiImpl
import com.example.furryroyals.auth.domain.AuthApi
import com.example.furryroyals.auth.presentation.login.AuthEventManager
import com.example.furryroyals.auth.presentation.login.LoginViewModel
import com.example.furryroyals.core.presentation.AuthViewModel
import com.example.furryroyals.core.data.networking.HttpClientFactory
import com.example.furryroyals.core.domain.UserRepository
import com.example.furryroyals.auth.presentation.account_detail.AccountDetailViewModel
import com.example.furryroyals.product.data.local.ProductDatabase
import com.example.furryroyals.product.data.remote.CategoryRepositoryImpl
import com.example.furryroyals.product.data.remote.ProductApi
import com.example.furryroyals.product.data.remote.ProductRepositoryImpl
import com.example.furryroyals.product.domain.CategoryRepository
import com.example.furryroyals.product.domain.Product
import com.example.furryroyals.product.domain.ProductRepository
import com.example.furryroyals.product.presentation.produtc_list.ProductViewModel
import io.ktor.client.engine.cio.CIO
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    single { HttpClientFactory.create(CIO.create()) }
    single { androidContext().getSharedPreferences("app_prefs", Context.MODE_PRIVATE) }
    single {
        Room.databaseBuilder(
            androidContext(),
            ProductDatabase::class.java,
            "product_database"
        ).fallbackToDestructiveMigration()
            .build()
    }
    single { get<ProductDatabase>().productDao }
    single { get<ProductDatabase>().categoryDao }
    singleOf(::ProductApi)
    singleOf(::UserRepository)
    singleOf(::AuthEventManager)
    singleOf(::ProductRepositoryImpl).bind<ProductRepository>()
    singleOf(::AuthApiImpl).bind<AuthApi>()
    singleOf(::CategoryRepositoryImpl).bind<CategoryRepository>()

    viewModelOf(::AuthViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::AccountDetailViewModel)
    viewModelOf(::ProductViewModel)
}
