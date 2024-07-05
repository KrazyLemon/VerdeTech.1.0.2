package com.krazylemon.verdetech102.pages.home

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.krazylemon.verdetech102.ApiViewModel
import com.krazylemon.verdetech102.ChatBotActivity
import com.krazylemon.verdetech102.HomeActivity
import com.krazylemon.verdetech102.R
import com.krazylemon.verdetech102.models.NavItem
import com.krazylemon.verdetech102.ui.theme.DarkGreen80

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    ApiViewModel: ApiViewModel,
    dataViewModel: ApiViewModel,
    context: Context
) {
    val navItemsList = listOf(
        NavItem("Soporte", R.drawable.ic_support),
        NavItem("Inicio", R.drawable.ic_home_filled),
        //NavItem("ChatBot", R.drawable.ic_chat_bubble),
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
            dataViewModel,
            context
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 100.dp, end = 30.dp),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Bottom
        ){
            ExtendedFloatingActionButton(
                onClick = {
                    val intent = Intent(context, ChatBotActivity::class.java)
                    context.startActivity(intent)
                },
                contentColor = MaterialTheme.colorScheme.onTertiary,
                containerColor = MaterialTheme.colorScheme.tertiary
            ) {
                Text(text = "Chat",
                    modifier = Modifier
                        .padding(end = 4.dp))
                Icon(ImageVector.vectorResource(id = R.drawable.ic_plantbot), contentDescription = "Icon")
            }

        }

    }
}


@Composable
fun ContentScreen(modifier: Modifier, selectedIndex : Int, ApiViewModel: ApiViewModel, dataViewModel: ApiViewModel,context: Context) {
    when(selectedIndex){
        0-> UsuarioPage(context)
        1-> HomeScreen(ApiViewModel,context)
        //2-> ChatbotScreen(ApiViewModel)
        2-> DatosScreen(dataViewModel)
    }
}

