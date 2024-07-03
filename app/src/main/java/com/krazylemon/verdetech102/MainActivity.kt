package com.krazylemon.verdetech102

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.krazylemon.verdetech102.pages.home.MainScreen
import com.krazylemon.verdetech102.pages.login.LoginScreen
import com.krazylemon.verdetech102.pages.login.PassScreen
import com.krazylemon.verdetech102.pages.login.RegisterScreen
import com.krazylemon.verdetech102.ui.theme.VerdeTech102Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ViewModelProvider(this)[ApiViewModel::class.java]
        setContent {
            VerdeTech102Theme{
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "login"){
                    composable(route = "home"){
                        MainScreen(navController,viewModel,this@MainActivity)
                    }
                    composable(route = "login"){
                        LoginScreen(navController, this@MainActivity )
                    }
                    composable(route = "registro"){
                        RegisterScreen(navController,this@MainActivity)
                    }
                    composable(route = "pass"){
                        PassScreen(navController,this@MainActivity)
                    }
                }
            }
        }
    }

}
