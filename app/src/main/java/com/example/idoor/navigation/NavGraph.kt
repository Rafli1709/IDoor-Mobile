package com.example.idoor.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.idoor.components.BottomBarAdmin
import com.example.idoor.components.BottomBarUser
import com.example.idoor.components.FloatingButton
import com.example.idoor.screen.OnBoardingScreen
import com.example.idoor.screen.OnBoardingViewModel
import com.example.idoor.screen.admin.HomeAdminScreen
import com.example.idoor.screen.admin.HomeAdminViewModel
import com.example.idoor.screen.admin.managetools.CreateToolScreen
import com.example.idoor.screen.admin.managetools.CreateToolViewModel
import com.example.idoor.screen.admin.managetools.ManageToolScreen
import com.example.idoor.screen.admin.managetools.ManageToolViewModel
import com.example.idoor.screen.admin.managetools.UpdateToolScreen
import com.example.idoor.screen.admin.managetools.UpdateToolViewModel
import com.example.idoor.screen.admin.manageuser.CreateUserScreen
import com.example.idoor.screen.admin.manageuser.CreateUserViewModel
import com.example.idoor.screen.admin.manageuser.ManageUserScreen
import com.example.idoor.screen.admin.manageuser.ManageUserViewModel
import com.example.idoor.screen.admin.manageuser.UpdateUserScreen
import com.example.idoor.screen.admin.manageuser.UpdateUserViewModel
import com.example.idoor.screen.auth.AuthViewModel
import com.example.idoor.screen.auth.LoginScreen
import com.example.idoor.screen.auth.RegisterScreen
import com.example.idoor.screen.user.HomeUserScreen
import com.example.idoor.screen.user.HomeUserViewModel
import com.example.idoor.screen.user.history.HistoryScreen
import com.example.idoor.screen.user.history.HistoryViewModel
import com.example.idoor.screen.user.manageaccess.CreateAccessScreen
import com.example.idoor.screen.user.manageaccess.CreateAccessViewModel
import com.example.idoor.screen.user.manageaccess.ManageAccessScreen
import com.example.idoor.screen.user.manageaccess.ManageAccessViewModel
import com.example.idoor.screen.user.manageusertool.CreateUserToolScreen
import com.example.idoor.screen.user.manageusertool.CreateUserToolViewModel
import com.example.idoor.screen.user.manageusertool.ManageUserToolScreen
import com.example.idoor.screen.user.manageusertool.ManageUserToolViewModel
import com.example.idoor.screen.user.manageusertool.UpdateUserToolScreen
import com.example.idoor.screen.user.manageusertool.UpdateUserToolViewModel
import com.example.idoor.screen.user.profile.ProfileScreen
import com.example.idoor.screen.user.profile.ProfileViewModel
import com.example.idoor.screen.user.profile.UpdateProfileScreen
import com.example.idoor.ui.theme.MidnightBlue

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = Screen.OnBoarding.route) {
            val viewModel: OnBoardingViewModel = hiltViewModel()
            OnBoardingScreen(navController = navController, viewModel = viewModel)
        }

        composable(route = Screen.Login.route) {
            val viewModel: AuthViewModel = hiltViewModel()
            LoginScreen(navController = navController, viewModel = viewModel)
        }

        composable(route = Screen.Register.route) {
            val viewModel: AuthViewModel = hiltViewModel()
            RegisterScreen(navController = navController, viewModel = viewModel)
        }

        composable(route = Screen.DashboardAdmin.route) {
            val viewModel: HomeAdminViewModel = hiltViewModel()
            Scaffold(
                bottomBar = { BottomBarAdmin(navController = navController) },
                containerColor = MidnightBlue,
            ) { innerPadding ->
                HomeAdminScreen(
                    viewModel = viewModel,
                    modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding())
                )
            }
        }

        composable(route = Screen.ManageUsers.route) {
            val viewModel: ManageUserViewModel = hiltViewModel()
            Scaffold(
                bottomBar = { BottomBarAdmin(navController = navController) },
                containerColor = MidnightBlue,
            ) { innerPadding ->
                ManageUserScreen(
                    navController = navController,
                    viewModel = viewModel,
                    modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding())
                )
            }
        }

        composable(route = Screen.AddUser.route) {
            val viewModel: CreateUserViewModel = hiltViewModel()
            CreateUserScreen(
                navController = navController,
                viewModel = viewModel,
            )
        }

        composable(
            route = Screen.UpdateUser.route,
            arguments = listOf(
                navArgument("id") {
                    type = NavType.IntType
                }
            )
        ) {
            val viewModel: UpdateUserViewModel = hiltViewModel()
            UpdateUserScreen(
                navController = navController,
                viewModel = viewModel,
            )
        }

        composable(route = Screen.ManageTools.route) {
            val viewModel: ManageToolViewModel = hiltViewModel()
            Scaffold(
                bottomBar = { BottomBarAdmin(navController = navController) },
                containerColor = MidnightBlue,
            ) { innerPadding ->
                ManageToolScreen(
                    navController = navController,
                    viewModel = viewModel,
                    modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding())
                )
            }
        }

        composable(route = Screen.AddTool.route) {
            val viewModel: CreateToolViewModel = hiltViewModel()
            CreateToolScreen(
                navController = navController,
                viewModel = viewModel,
            )
        }

        composable(
            route = Screen.UpdateTool.route,
            arguments = listOf(
                navArgument("id") {
                    type = NavType.IntType
                }
            )
        ) {
            val viewModel: UpdateToolViewModel = hiltViewModel()
            UpdateToolScreen(
                navController = navController,
                viewModel = viewModel,
            )
        }

        composable(route = Screen.DashboardUser.route) {
            val viewModel: HomeUserViewModel = hiltViewModel()
            Scaffold(
                bottomBar = { BottomBarUser(navController = navController) },
                containerColor = MidnightBlue,
            ) { innerPadding ->
                HomeUserScreen(
                    viewModel = viewModel,
                    modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding())
                )
            }
        }

        composable(route = Screen.ManageUserTools.route) {
            val viewModel: ManageUserToolViewModel = hiltViewModel()
            Scaffold(
                bottomBar = { BottomBarUser(navController = navController) },
                containerColor = MidnightBlue,
            ) { innerPadding ->
                ManageUserToolScreen(
                    navController = navController,
                    viewModel = viewModel,
                    modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding())
                )
            }
        }

        composable(route = Screen.AddUserTool.route) {
            val viewModel: CreateUserToolViewModel = hiltViewModel()
            CreateUserToolScreen(
                navController = navController,
                viewModel = viewModel,
            )
        }

        composable(
            route = Screen.UpdateUserTool.route,
            arguments = listOf(
                navArgument("id") {
                    type = NavType.IntType
                }
            )
        ) {
            val viewModel: UpdateUserToolViewModel = hiltViewModel()
            UpdateUserToolScreen(
                navController = navController,
                viewModel = viewModel
            )
        }

        composable(
            route = Screen.ManageAccess.route,
            arguments = listOf(
                navArgument("id") {
                    type = NavType.IntType
                }
            )
        ) {
            val viewModel: ManageAccessViewModel = hiltViewModel()
            ManageAccessScreen(
                navController = navController,
                viewModel = viewModel
            )
        }

        composable(
            route = Screen.AddAccess.route,
            arguments = listOf(
                navArgument("id") {
                    type = NavType.IntType
                }
            )
        ) {
            val viewModel: CreateAccessViewModel = hiltViewModel()
            CreateAccessScreen(
                navController = navController,
                viewModel = viewModel,
            )
        }

        composable(route = Screen.HistoryAccess.route) {
            val viewModel: HistoryViewModel = hiltViewModel()
            Scaffold(
                bottomBar = { BottomBarUser(navController = navController) },
                containerColor = MidnightBlue,
            ) { innerPadding ->
                HistoryScreen(
                    viewModel = viewModel,
                    modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding())
                )
            }
        }

        composable(route = Screen.Profile.route) {
            val viewModel: ProfileViewModel = hiltViewModel()
            Scaffold(
                bottomBar = { BottomBarUser(navController = navController) },
                containerColor = MidnightBlue,
            ) { innerPadding ->
                ProfileScreen(
                    navController = navController,
                    viewModel = viewModel,
                    modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding())
                )
            }
        }

        composable(route = Screen.UpdateProfile.route) {
            val viewModel: ProfileViewModel = hiltViewModel()
            UpdateProfileScreen(
                navController = navController,
                viewModel = viewModel
            )
        }
    }
}