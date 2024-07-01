package com.krazylemon.verdetech102

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.krazylemon.verdetech102.nav.Nav
import com.krazylemon.verdetech102.ui.theme.VerdeTech102Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val apiViewModel = ViewModelProvider(this)[ApiViewModel::class.java]
        setContent {
            VerdeTech102Theme{
                Nav(apiViewModel)
            }
        }
    }
}
