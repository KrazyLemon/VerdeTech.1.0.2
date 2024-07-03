package com.krazylemon.verdetech102.pages.home

import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavController
import com.krazylemon.verdetech102.ApiViewModel
import com.krazylemon.verdetech102.R
import com.krazylemon.verdetech102.models.NavItem
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavController,
    ApiViewModel: ApiViewModel,
    context: Context
) {
    val navItemsList = listOf(
        NavItem("Usuario", R.drawable.ic_person),
        NavItem("Inicio", R.drawable.ic_home_filled),
        NavItem("ChatBot", R.drawable.ic_chat_bubble),
        NavItem("Datos", R.drawable.ic_analitics)
    )
    var selectedIndex by remember { mutableIntStateOf(1) }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        bottomBar = {
                NavigationBar {
                    navItemsList.forEachIndexed { index, navItem ->
                        NavigationBarItem(
                            selected = selectedIndex == index,
                            onClick = {
                                selectedIndex = index
                            },
                            icon = {
                                Icon(ImageVector.vectorResource(id = navItem.icon), contentDescription = "Icono")
                            },
                            label = {
                                Text(text = navItem.label)
                            }
                        )
                    }
                }
        },
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground
    ) { innerPadding ->
        ContentScreen(
            modifier = Modifier.padding(innerPadding),
            selectedIndex,
            ApiViewModel,
            context
        )
    }
}


@Composable
fun ContentScreen(modifier: Modifier, selectedIndex : Int, ApiViewModel: ApiViewModel, context: Context) {
    when(selectedIndex){
        0-> UsuarioPage(ApiViewModel)
        1-> HomeScreen(ApiViewModel,context)
        2-> ChatbotScreen(ApiViewModel)
        3-> DatosScreen(ApiViewModel)
    }
}
