package com.krazylemon.verdetech102.pages.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.krazylemon.verdetech102.ApiViewModel
import com.krazylemon.verdetech102.R

@Composable
fun DatosScreen(viewModel: ApiViewModel){

    val action = "output_date"

    LaunchedEffect(Unit) {
        viewModel.getDataByDate(action,"2024-06-29","2024-07-01")
    }
    Column(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 10.dp)
        ){
            Text(
                text = "Historial de Lecturas",
                color = Color.Black,
                fontSize = 24.sp
            )
            IconButton(
                modifier = Modifier
                    .padding(0.dp),
                onClick = { viewModel.getDataByDate(action,"2024-06-29","2024-07-01") },
            ) {
                Icon(painter = painterResource(id = R.drawable.ic_refresh) , contentDescription = "RefreshIcon" )
            }
        }
        Row(modifier = Modifier.padding(4.dp)) {
            MyDatePicker()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDatePicker() {
    val dateState = rememberDatePickerState()

    DatePicker(state = dateState)

}
