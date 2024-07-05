package com.krazylemon.verdetech102.pages.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.krazylemon.verdetech102.ApiViewModel
import com.krazylemon.verdetech102.R
import com.krazylemon.verdetech102.api.NetworkResponse
import com.krazylemon.verdetech102.models.DhtModel
import com.krazylemon.verdetech102.models.Message
import kotlinx.coroutines.delay
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatosScreen(viewModel: ApiViewModel){
    val action = "output_date"
    val dataResult = viewModel.DhtResult.observeAsState()

    var showDialog  by remember {
        mutableStateOf(false)
    }

    var firstDate by remember {
        mutableStateOf("")
    }
    var secondDate by remember {
        mutableStateOf("")
    }
    var error by remember {
        mutableStateOf("")
    }
    val state = rememberDateRangePickerState()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = "Historal de Lecturas",
                modifier = Modifier.padding(16.dp),
                color = MaterialTheme.colorScheme.background,
                fontSize = 22.sp
            )
            FilledIconButton(
                onClick = { showDialog  = true }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_date),
                    contentDescription = "Fecha Icon",
                    tint = Color.White
                )
            }
        }
    }
    
    Column(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Spacer(modifier = Modifier.height(50.dp))
        Text(text = "Fecha inicial: ${firstDate} ")
        Text(text = "Fecha final: ${secondDate} ")
        Text(
            text = error,
            color = MaterialTheme.colorScheme.error,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
        if(showDialog){
            DatePickerDialog(
                onDismissRequest = { showDialog  = false },
                confirmButton = { 
                    Button(
                        onClick = {
                            val a = state.selectedStartDateMillis
                            val b = state.selectedEndDateMillis
                            a?.let {
                                val date_a = Instant.ofEpochMilli(a).atZone(ZoneId.of("UTC")).toLocalDate()
                                firstDate = "${date_a.year}-${date_a.monthValue}-${date_a.dayOfMonth}"
                            }
                            b?.let {
                                val date_b = Instant.ofEpochMilli(b).atZone(ZoneId.of("UTC")).toLocalDate()
                                secondDate = "${date_b.year}-${date_b.monthValue}-${date_b.dayOfMonth}"
                            }
                            showDialog  = false
                            if(firstDate != "" || secondDate != "" ){
                                error = ""
                                viewModel.getDataByDate(action,firstDate,secondDate)
                            }else{
                                error = "Elige ambas fechas para mostrar datos"
                            }
                        }
                    ) {
                        Text(text = "Confirmar")
                    }
                }
            ) {
                DateRangePicker(
                    state = state,
                    modifier = Modifier
                        .background( color = MaterialTheme.colorScheme.background)
                )
            }
        }
        
        when(val result = dataResult.value){
            is NetworkResponse.Error -> {
                Text(text = result.message)
            }
            NetworkResponse.Loading -> {
                CircularProgressIndicator()
            }
            is NetworkResponse.Success -> {
                TablaData(data = result.data)
                //Text(text = result.data.toString())
            }
            null -> {}
        }
    }
}

@Composable
fun TablaData(data: DhtModel) {
    var itemCount = data.message.size
    var dataList = data.message
    Text(text = "Elementos Encontrados: ${itemCount}")
    LazyColumn {
        items(dataList){ mensaje->
            RowTabla(mensaje)
        }
    }
}

@Composable
fun RowTabla(mensaje: Message ){

    var a = mensaje.smp_a.toInt()
    var b = mensaje.smp_a.toInt()
    var c = mensaje.smp_a.toInt()
    var d = mensaje.smp_a.toInt()
    var e = mensaje.smp_a.toInt()

    var prom = (a + b + c + d + e) / 5
    var res = ( prom * 100 ) / 4095
    var hum = 100 - res

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .padding(vertical = 4.dp, horizontal = 10.dp)
            .background(
                color = setBGColor(hum).copy(alpha = .5f),
                shape = MaterialTheme.shapes.small
            ),
    ) {
        Column(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxHeight()
        ) {
            Row(
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    modifier = Modifier
                        .padding(horizontal = 6.dp, vertical = 1.dp)
                        .background(
                            color = MaterialTheme.colorScheme.surface,
                            shape = MaterialTheme.shapes.small
                        ),
                    text = "Fecha: ${mensaje.posted_at}",
                    color = Color.Black,
                    fontSize = 10.sp,
                )
            }
            Row(
                modifier = Modifier
                    .padding(vertical = 1.dp),
            ){
                Text(
                    modifier = Modifier
                        .padding( horizontal = 4.dp),
                    text = "Humedad: ${mensaje.hum}%",
                    color = Color.Black,
                    fontSize = 10.sp,
                )
                Text(
                    modifier = Modifier
                        .padding( horizontal = 4.dp),
                    text = "temperatura: ${mensaje.temp}°C",
                    color = Color.Black,
                    fontSize = 10.sp,
                )
                Text(
                    modifier = Modifier
                        .padding( horizontal = 4.dp),
                    text = "Indice de Calor: ${mensaje.heat} J",
                    color = Color.Black,
                    fontSize = 10.sp,
                )
            }
            Row(
                modifier = Modifier
                    .padding(vertical = 1.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(
                    modifier = Modifier
                        .padding( horizontal = 2.dp),
                    text = "S1: ${mensaje.smp_a}%",
                    color = Color.Black,
                    fontSize = 10.sp,
                )
                Text(
                    modifier = Modifier
                        .padding( horizontal = 2.dp),
                    text = "S2: ${mensaje.smp_b}%",
                    color = Color.Black,
                    fontSize = 10.sp,
                )
                Text(
                    modifier = Modifier
                        .padding( horizontal = 2.dp),
                    text = "S3: ${mensaje.smp_c}%",
                    color = Color.Black,
                    fontSize = 10.sp,
                )
                Text(
                    modifier = Modifier
                        .padding( horizontal = 2.dp),
                    text = "S4: ${mensaje.smp_d}%",
                    color = Color.Black,
                    fontSize = 10.sp,
                )
                Text(
                    modifier = Modifier
                        .padding( horizontal = 2.dp),
                    text = "S5: ${mensaje.smp_e}%",
                    color = Color.Black,
                    fontSize = 10.sp,
                )
            }
        }
    }
}

fun setBGColor(hum : Int):Color{
    var color = Color(0xFFFFFFFF)

    if(hum > 85){
        color = Color(0xFF00FF13)
    }else{
        if( hum in 51..85){
            color = Color(0xFFFFE100)
        }else{
            if(hum in 31..50){
                color = Color(0xFFFF7600)
            }else{
                color = Color(0xFFFF2500)
            }
        }
    }
    return color
}