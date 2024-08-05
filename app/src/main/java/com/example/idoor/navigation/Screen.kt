package com.example.idoor.navigation

sealed class Screen(val route: String) {
    data object OnBoarding : Screen("onBoarding")
    data object Login : Screen("login")
    data object Register : Screen("register")
    data object DashboardAdmin : Screen("dashboardAdmin")
    data object ManageUsers : Screen("manageUsers")
    data object AddUser : Screen("addUsers")
    data object UpdateUser : Screen("updateUsers/{id}")
    data object ManageTools : Screen("manageTools")
    data object AddTool : Screen("addTools")
    data object UpdateTool : Screen("updateTools/{id}")
    data object DashboardUser : Screen("dashboardUser")
    data object ManageKeys : Screen("manageKeys")
    data object ManageUserTools : Screen("manageUserTools")
    data object AddUserTool : Screen("addUserTools")
    data object UpdateUserTool : Screen("updateUserTools/{id}")
    data object ManageAccess : Screen("manageAccess/{id}")
    data object AddAccess : Screen("addAccess/{id}")
    data object HistoryAccess : Screen("historyAccess")
    data object Profile : Screen("profile")
    data object UpdateProfile : Screen("updateProfile")
}
