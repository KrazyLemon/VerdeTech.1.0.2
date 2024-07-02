package com.krazylemon.verdetech102.pages.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.krazylemon.verdetech102.ApiViewModel
import com.krazylemon.verdetech102.api.DhtApi

@Composable
fun UsuarioPage(viewModel: ApiViewModel){
    val DhtResult = viewModel.DhtResult.observeAsState()

    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .fillMaxHeight()
    ) {

    }
}