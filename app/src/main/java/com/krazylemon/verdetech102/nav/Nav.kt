package com.krazylemon.verdetech102.nav

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.krazylemon.verdetech102.ApiViewModel
import com.krazylemon.verdetech102.MainScreen
import com.krazylemon.verdetech102.pages.login.LoginScreen
import com.krazylemon.verdetech102.pages.login.RegisterScreen

@Composable
fun Nav(viewModel: ApiViewModel){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login"){

        composable(route = "home"){
            MainScreen(navController,viewModel)
        }
        composable(route = "login"){
            LoginScreen(navController)
        }
        composable(route = "registro"){
            RegisterScreen(navController)
        }
    }

}