package com.example.furryroyals.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.furryroyals.auth.presentation.login.LoginViewModel
import com.example.furryroyals.core.presentation.AuthViewModel
import com.example.furryroyals.core.presentation.nav_items.bottomNav.BottomNavigationItems
import com.example.furryroyals.ui.cart.CartScreen
import com.example.furryroyals.product.presentation.produtc_list.HomeScreen
import com.example.furryroyals.ui.profile.ProfileScreen
import com.example.furryroyals.auth.presentation.account_detail.AccountDetailScreen
import com.example.furryroyals.auth.presentation.account_detail.AccountDetailViewModel
import com.example.furryroyals.auth.presentation.account_detail.contact.ContactInfoScreen
import com.example.furryroyals.auth.presentation.account_detail.update.OtpDialog
import com.example.furryroyals.auth.presentation.account_detail.update.UpdateEmailScreen
import com.example.furryroyals.auth.presentation.account_detail.update.UpdateNameScreen
import com.example.furryroyals.product.presentation.produtc_list.ProductListScreen
import com.example.furryroyals.product.presentation.produtc_list.ProductViewModel
import com.example.furryroyals.ui.profile.address.AddressScreen
import com.example.furryroyals.ui.profile.orders.MyOrdersScreen
import com.example.furryroyals.ui.profile.signout.SignOutDialog
import org.koin.androidx.compose.koinViewModel
import timber.log.Timber

sealed class Screen(val route: String) {
    data object ProductList : Screen("ProductList")
    data object AccountDetail : Screen("AccountDetail")
    data object Address : Screen("Address")
    data object MyOrders : Screen("MyOrders")
    data object UpdateEmail : Screen("UpdateEmail")
    data object ContactInfo : Screen("ContactInfo")
    data object UpdateUsername : Screen("UpdateUsername")
}

@Composable
fun AppNavigation(
    navController: NavHostController,
    onBottomBarVisibilityChanged: (Boolean) -> Unit,
    onButtonsVisibilityChanged: (Boolean) -> Unit
) {
    val productViewModel = koinViewModel<ProductViewModel>()
    val productListState by productViewModel.productListState.collectAsStateWithLifecycle()
    val categoryListState by productViewModel.categoryListState.collectAsStateWithLifecycle()
    val authViewModel = koinViewModel<AuthViewModel>()
    val isLoggedIn by authViewModel.isLoggedIn.collectAsStateWithLifecycle()
    val resetKey by authViewModel.resetKey.collectAsStateWithLifecycle()

    key(resetKey) {
        val loginViewModel = koinViewModel<LoginViewModel>()
        val accountDetailViewModel = koinViewModel<AccountDetailViewModel>()
        val accountDetailUiState by accountDetailViewModel.accountDetailUiState.collectAsStateWithLifecycle()
        val emailVerificationUiState by accountDetailViewModel.emailVerificationUiState.collectAsStateWithLifecycle()
        val username = accountDetailUiState.username
        val email = accountDetailUiState.email
        val phoneNumber = accountDetailUiState.phoneNumber


        Timber.tag("shared").i("phonenumber %s", phoneNumber)
        Timber.tag("shared").i("username %s", username)

        var openDialog by remember { mutableStateOf(false) }
        var openOTPDialog by remember { mutableStateOf(false) }

        NavHost(
            navController = navController,
            startDestination = BottomNavigationItems.Home.route
        ) {

            composable(route = BottomNavigationItems.Home.route) {
                onBottomBarVisibilityChanged(true)
                onButtonsVisibilityChanged(false)
                HomeScreen(
                    productListState = productListState,
                    categoryListState = categoryListState
                )
            }

            composable(route = BottomNavigationItems.Cart.route) {
                onBottomBarVisibilityChanged(true)
                onButtonsVisibilityChanged(false)
                CartScreen()
            }

            composable(route = BottomNavigationItems.WatchList.route) {
                onBottomBarVisibilityChanged(true)
                onButtonsVisibilityChanged(false)
                ProductListScreen(
                    productViewModel = productViewModel,
                    productListState = productListState
                )
            }

            composable(route = BottomNavigationItems.Profile.route) {
                onBottomBarVisibilityChanged(true)
                onButtonsVisibilityChanged(false)
                ProfileScreen(
                    phoneNumber = phoneNumber,
                    onAccountDetailClick = {
                        navController.navigate(Screen.AccountDetail.route)
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
                    onSignOutClick = { openDialog = true },
                    isLoggedIn = isLoggedIn,
                    loginViewModel = loginViewModel,
                    onDismiss = {
                        navController.navigate(BottomNavigationItems.Home.route) {
                            popUpTo(BottomNavigationItems.Profile.route) { inclusive = true }
                            loginViewModel.resetState()
                        }
                    }
                )
                if (openDialog) {
                    SignOutDialog(
                        onDismiss = { openDialog = false },
                        onSignOutConfirm = {
                            authViewModel.logout()
                            openDialog = false
                        }
                    )

                }
            }

            composable(route = Screen.AccountDetail.route) {
                onBottomBarVisibilityChanged(false)
                onButtonsVisibilityChanged(false)
                AccountDetailScreen(
                    username = username,
                    email = email.ifEmpty { "Set your email" },
                    phoneNumber = phoneNumber,
                    onContactClick = { navController.navigate(route = Screen.ContactInfo.route) },
                    onNameClick = { navController.navigate(route = Screen.UpdateUsername.route) }
                )
            }
            composable(route = Screen.ContactInfo.route) {
                onBottomBarVisibilityChanged(false)
                onButtonsVisibilityChanged(false)
                ContactInfoScreen(
                    email = email.ifEmpty { "Email" },
                    phoneNumber = phoneNumber,
                    onEmailClick = { navController.navigate(route = Screen.UpdateEmail.route) })
            }

            composable(route = Screen.UpdateEmail.route) {
                onBottomBarVisibilityChanged(false)
                onButtonsVisibilityChanged(false)
                UpdateEmailScreen(
                    emailVerificationUiState = emailVerificationUiState,
                    accountDetailViewModel = accountDetailViewModel,
                    openOtpDialog = openOTPDialog,
                    onOtpSent = { openOTPDialog = true }
                )

                if (openOTPDialog) {
                    OtpDialog(
                        accountDetailViewModel = accountDetailViewModel,
                        emailVerificationUiState = emailVerificationUiState,
                        onDismiss = { openOTPDialog = false },
                        onSuccess = {
                            openOTPDialog = false
                            navController.navigate(Screen.AccountDetail.route) {
                                popUpTo(route = Screen.ContactInfo.route) { inclusive = true }
                                popUpTo(route = Screen.UpdateEmail.route) { inclusive = true }
                                accountDetailViewModel.resetEmailVerificationUiState()
                            }
                        }
                    )
                }
            }

            composable(route = Screen.UpdateUsername.route) {
                onBottomBarVisibilityChanged(false)
                onButtonsVisibilityChanged(false)
                UpdateNameScreen(
                    accountDetailUiState = accountDetailUiState,
                    accountDetailViewModel = accountDetailViewModel
                )
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

            composable(route = Screen.ProductList.route) {
                onBottomBarVisibilityChanged(true)
                onButtonsVisibilityChanged(false)
                ProductListScreen(
                    productViewModel = productViewModel,
                    productListState = productListState
                )
            }

        }
    }


}
