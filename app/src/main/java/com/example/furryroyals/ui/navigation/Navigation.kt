package com.example.furryroyals.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.furryroyals.bottomNav.BottomNavigationItems
import com.example.furryroyals.ui.auth.AuthViewModel
import com.example.furryroyals.ui.auth.login.AnimatedLoginScreen
import com.example.furryroyals.ui.auth.login.LoginViewModel
import com.example.furryroyals.ui.auth.registration.AnimatedOtpScreen
import com.example.furryroyals.ui.auth.registration.AnimatedRegisterFinalScreen
import com.example.furryroyals.ui.auth.registration.AnimatedRegisterScreen
import com.example.furryroyals.ui.auth.registration.RegistrationViewModel
import com.example.furryroyals.ui.cart.CartScreen
import com.example.furryroyals.ui.category.CategoryScreen
import com.example.furryroyals.ui.home.HomeScreen
import com.example.furryroyals.ui.profile.ProfileScreen
import com.example.furryroyals.ui.profile.ProfileViewModel
import com.example.furryroyals.ui.profile.accountDetail.AccountDetailScreen
import com.example.furryroyals.ui.profile.address.AddressScreen
import com.example.furryroyals.ui.profile.orders.MyOrdersScreen
import com.example.furryroyals.ui.profile.signout.SignOutDialog

sealed class Screen(val route: String) {
    data object Login : Screen("Login")
    data object Register : Screen("Register")
    data object Otp : Screen("Otp")
    data object FinalRegister : Screen("FinalRegister")
    data object AccountDetail : Screen("AccountDetail")
    data object Address : Screen("Address")
    data object MyOrders : Screen("MyOrders")
}

@Composable
fun AppNavigation(
    navController: NavHostController,
    onBottomBarVisibilityChanged: (Boolean) -> Unit,
    onButtonsVisibilityChanged: (Boolean) -> Unit
) {
    val authViewModel: AuthViewModel = hiltViewModel()
    val isLoggedIn by authViewModel.isLoggedIn.collectAsStateWithLifecycle()
    val resetKey by authViewModel.resetKey.collectAsStateWithLifecycle()

    key(resetKey) {
        val registrationViewModel: RegistrationViewModel = hiltViewModel()
        val registrationUiState by registrationViewModel.registrationUiState.collectAsStateWithLifecycle()

        val loginViewModel: LoginViewModel = hiltViewModel()
        val loginUiState by loginViewModel.loginUiState.collectAsStateWithLifecycle()


        val profileViewModel: ProfileViewModel = hiltViewModel()
        val profileUiState by profileViewModel.profileUiState.collectAsStateWithLifecycle()

        var openDialog by remember { mutableStateOf(false) }

        NavHost(navController = navController, startDestination = BottomNavigationItems.Home.route) {

            composable(route = BottomNavigationItems.Home.route) {
                onBottomBarVisibilityChanged(true)
                onButtonsVisibilityChanged(false)
                HomeScreen(navController = navController)
            }

            composable(route = BottomNavigationItems.Cart.route) {
                onBottomBarVisibilityChanged(true)
                onButtonsVisibilityChanged(false)
                CartScreen()
            }

            composable(route = BottomNavigationItems.Category.route) {
                onBottomBarVisibilityChanged(true)
                onButtonsVisibilityChanged(false)
                CategoryScreen()
            }

            composable(route = BottomNavigationItems.Profile.route) {
                onBottomBarVisibilityChanged(true)
                onButtonsVisibilityChanged(false)
                if (isLoggedIn) {
                    ProfileScreen(
                        profileUiState = profileUiState,
                        onAccountDetailClick = {
                            navController.navigate(Screen.AccountDetail.route) {
                                popUpTo(BottomNavigationItems.Profile.route) { inclusive = true }
                            }
                        },
                        onOrdersClick = {
                            navController.navigate(Screen.MyOrders.route) {
                                popUpTo(BottomNavigationItems.Profile.route) { inclusive = true }
                            }
                        },

                        onAddressClick = {
                            navController.navigate(Screen.Address.route) {
                                popUpTo(BottomNavigationItems.Profile.route) { inclusive = true }
                            }
                        },
                        onSignOutClick = { openDialog = true }
                    )
                    if (openDialog) {
                        onBottomBarVisibilityChanged(true)
                        SignOutDialog(
                            onDismiss = { openDialog = false },
                            onSignOutConfirm = {
                                authViewModel.logout()
                                openDialog = false
                            }
                        )

                    }
                } else {
                    AnimatedLoginScreen(
                        onLoginSuccess = {
                            navController.navigate(BottomNavigationItems.Home.route) {
                                popUpTo(BottomNavigationItems.Profile.route) { inclusive = true }
                            }
                        },
                        onSingUpClick = { navController.navigate(Screen.Register.route) },
                        loginViewModel = loginViewModel,
                        loginUiState = loginUiState
                    )
                }
            }

            composable(route = Screen.AccountDetail.route) {
                onBottomBarVisibilityChanged(true)
                onButtonsVisibilityChanged(false)
                AccountDetailScreen()
            }

            composable(route = Screen.Address.route) {
                onBottomBarVisibilityChanged(true)
                onButtonsVisibilityChanged(false)
                AddressScreen()
            }

            composable(route = Screen.MyOrders.route) {
                onBottomBarVisibilityChanged(true)
                onButtonsVisibilityChanged(false)
                MyOrdersScreen()
            }

            composable(route = Screen.Register.route) {
                onBottomBarVisibilityChanged(true)
                onButtonsVisibilityChanged(false)
                AnimatedRegisterScreen(
                    onSuccess = { navController.navigate(Screen.Otp.route) },
                    onSignInClick = { navController.navigate(Screen.Login.route) },
                    registrationViewModel = registrationViewModel,
                    registrationUiState = registrationUiState
                )
            }

            composable(route = Screen.Otp.route) {
                onBottomBarVisibilityChanged(true)
                onButtonsVisibilityChanged(false)
                AnimatedOtpScreen(
                    onSuccess = { navController.navigate(Screen.FinalRegister.route) },
                    registrationViewModel = registrationViewModel,
                    registrationUiState = registrationUiState
                )
            }

            composable(route = Screen.FinalRegister.route) {
                onBottomBarVisibilityChanged(true)
                onButtonsVisibilityChanged(false)
                AnimatedRegisterFinalScreen(
                    onSuccess = {
                        navController.navigate(BottomNavigationItems.Home.route) {
                            popUpTo(Screen.Register.route) { inclusive = true }
                            popUpTo(Screen.Otp.route) { inclusive = true }
                            popUpTo(Screen.FinalRegister.route) { inclusive = true }
                        }
                    },
                    registrationViewModel = registrationViewModel,
                    registrationUiState = registrationUiState
                )
            }
        }
    }




}
