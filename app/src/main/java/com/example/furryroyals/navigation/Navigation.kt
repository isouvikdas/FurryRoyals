package com.example.furryroyals.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.furryroyals.ui.HomeScreen
import com.example.furryroyals.ui.auth.login.AnimatedLoginScreen
import com.example.furryroyals.ui.auth.registration.AnimatedOtpScreen
import com.example.furryroyals.ui.auth.registration.AnimatedRegisterFinalScreen
import com.example.furryroyals.ui.auth.registration.AnimatedRegisterScreen
import com.example.furryroyals.ui.auth.registration.RegistrationViewModel

sealed class Screen(val route: String) {
    data object Login : Screen("Login")
    data object Register : Screen("Register")
    data object Otp : Screen("Otp")
    data object FinalRegister : Screen("FinalRegister")
    data object Home : Screen("Home")
}

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController(),
    registrationViewModel: RegistrationViewModel = hiltViewModel()
) {
    NavHost(navController = navController, startDestination = Screen.Login.route) {
        composable(route = Screen.Login.route) {
            AnimatedLoginScreen(
                onLoginSuccess = { navController.navigate(Screen.Home.route) },
                onSingUpClick = { navController.navigate(Screen.Register.route) }
            )
        }

        composable(route = Screen.Home.route) {
            HomeScreen()
        }

        composable(route = Screen.Register.route) {
            AnimatedRegisterScreen(
                onSuccess = { navController.navigate(Screen.Otp.route) },
                onSignInClick = { navController.navigate(Screen.Login.route) },
                registrationViewModel = registrationViewModel
            )
        }

        composable(route = Screen.Otp.route) {
            AnimatedOtpScreen(
                onSuccess = { navController.navigate(Screen.FinalRegister.route) },
                onSignInClick = { navController.navigate(Screen.Login.route) },
                registrationViewModel = registrationViewModel
            )
        }

        composable(route = Screen.FinalRegister.route) {
            AnimatedRegisterFinalScreen(
                onSuccess = { navController.navigate(Screen.Home.route) },
                onSignInClick = { navController.navigate(Screen.Login.route) },
                registrationViewModel = registrationViewModel
            )
        }
    }
}