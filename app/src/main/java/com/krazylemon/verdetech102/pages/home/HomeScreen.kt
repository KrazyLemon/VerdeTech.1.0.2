package com.krazylemon.verdetech102.pages.home

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.krazylemon.verdetech102.ApiViewModel
import com.krazylemon.verdetech102.R
import com.krazylemon.verdetech102.models.DhtModel
import com.krazylemon.verdetech102.api.NetworkResponse
import kotlinx.coroutines.delay
import androidx.compose.ui.graphics.graphicsLayer
import androidx.lifecycle.viewmodel.compose.viewModel
import com.krazylemon.verdetech102.api.OutputResponse
import com.krazylemon.verdetech102.models.OutputList

@Composable
fun HomeScreen(viewModel: ApiViewModel, context: Context){

    val DhtResult = viewModel.DhtResult.observeAsState()
    val OutputsResult = viewModel.OutputResult.observeAsState()
    
    val action = "output_limit"

    LaunchedEffect(Unit) {
        while (true) {
            viewModel.getData(action,1)
            viewModel.getOutputsState()
            delay(60000) // 60000 ms = 1 minuto
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,

    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 10.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Text(
                text = "Lecturas del sistema",
                fontSize = 24.sp,
                lineHeight = 24.sp
            )
            IconButton(
                onClick = { viewModel.getData(action,1) },
            ) {
                Icon(painter = painterResource(id = R.drawable.ic_refresh) , contentDescription = "RefreshIcon" )
            }
        }
        when(val result = DhtResult.value){
            is NetworkResponse.Error -> {
                Text(text = result.message)
            }
            NetworkResponse.Loading -> {
                CircularProgressIndicator()
            }
            is NetworkResponse.Success -> {
                DhtDetails(viewModel, result.data)
            }
            null -> {}
        }
        when(val result = OutputsResult.value){
            is OutputResponse.Error ->{
                Text(text = result.message)
            }
            OutputResponse.Loading ->{
                //CircularProgressIndicator()
            }
            is OutputResponse.Success ->{
                BombaButton(viewModel,result.data)
            }
            null->{}
        }
    }
}


@Composable
fun BombaButton(viewModel : ApiViewModel,data: OutputList) {
    var puerto = false
    if(data.message[1].state != "1") puerto = true
    else puerto = false

    var bombaState by remember { mutableStateOf(puerto) }

    Row {
        Text(
            if(!bombaState) "Bomba Desactivada" else "Bomba Activada",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Switch(
            checked = bombaState,
            onCheckedChange = {
                if (!bombaState) viewModel.updateState(1)
                else viewModel.updateState(0)
                bombaState = it
            }
        )
    }

}

@Composable
fun DhtDetails(viewModel : ApiViewModel,data : DhtModel){
    var a = data.message[0].smp_a.toInt()
    var b = data.message[0].smp_b.toInt()
    var c = data.message[0].smp_c.toInt()
    var d = data.message[0].smp_d.toInt()
    var e = data.message[0].smp_e.toInt()
    var prom = (a + b + c + d + e) / 5
    var res = ( prom * 100 ) / 4095
    var hum = 100 - res
    var estado = ""

    if(hum.toFloat() >= 85 ){
        estado = "Excelente"
    }else{
        if( 70 > hum.toFloat() && hum.toFloat() <= 50){
            estado = "Bueno"
        }else{
            if( 30 > hum.toFloat() && hum.toFloat() <= 10){
                estado = "Regular"
            }else{
                estado = "Malo"
            }
        }
    }

    Column(
        modifier = Modifier
            .padding(4.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Estado General:",
            fontSize = 32.sp,
            lineHeight = 32.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .fillMaxWidth()
        )
        Box(
            modifier = Modifier
                .padding(16.dp)
                .size(180.dp),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = estado.toString(),
                fontSize = 20.sp,
                fontWeight = FontWeight.Light
            )
            CircularProgressIndicator(
                progress = hum.toFloat() ,
                color = setProgressColor(hum.toInt()),
                strokeWidth = 16.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
            )
        }
        Row{
            Box(
                modifier = Modifier
                    .padding(4.dp)
            ){
                Box(
                    modifier = Modifier
                        .graphicsLayer {
                            shadowElevation = 8.dp.toPx()
                            shape = RoundedCornerShape(8.dp)
                            clip = true
                            translationX = 8.dp.toPx()
                            translationY = 4.dp.toPx()
                        }
                        .background(
                            color = Color(0xFFf96c6c),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(10.dp)
                ){
                    Column(
                        modifier = Modifier
                        ,
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = "Temperatura",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.background
                        )
                        Text(
                            text = "${ data.message[0].temp }°C",
                            fontWeight = FontWeight.Bold,
                            fontSize = 40.sp,
                            color = MaterialTheme.colorScheme.background
                        )
                    }

                }
            }
            Box(
                modifier = Modifier
                    .padding(4.dp)
            ){
                Box(
                    modifier = Modifier
                        .graphicsLayer {
                            shadowElevation = 8.dp.toPx()
                            shape = RoundedCornerShape(8.dp)
                            clip = true
                            translationX = 8.dp.toPx()
                            translationY = 4.dp.toPx()
                        }
                        .background(
                            color = Color(0xFF6cbdf9),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(10.dp)
                ){
                    Column(
                        modifier = Modifier
                        ,
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = "Humedad",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.background
                        )
                        Text(
                            text = "${ data.message[0].hum }%",
                            fontWeight = FontWeight.Bold,
                            fontSize = 40.sp,
                            color = MaterialTheme.colorScheme.background
                        )
                    }

                }
            }
        }
        Row{
            Box(
                modifier = Modifier
                    .padding(4.dp)
            ){
                Box(
                    modifier = Modifier
                        .graphicsLayer {
                            shadowElevation = 8.dp.toPx()
                            shape = RoundedCornerShape(8.dp)
                            clip = true
                            translationX = 8.dp.toPx()
                            translationY = 4.dp.toPx()
                        }
                        .background(
                            color = Color(0xFFfbbc48),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(10.dp)
                ){
                    Column(
                        modifier = Modifier
                        ,
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = "Calor",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.background
                        )
                        Text(
                            text = "${ data.message[0].heat } J",
                            fontWeight = FontWeight.Bold,
                            fontSize = 40.sp,
                            color = MaterialTheme.colorScheme.background
                        )
                    }

                }
            }
            Box(
                modifier = Modifier
                    .padding(4.dp)
            ){
                Box(
                    modifier = Modifier
                        .graphicsLayer {
                            shadowElevation = 8.dp.toPx()
                            shape = RoundedCornerShape(8.dp)
                            clip = true
                            translationX = 8.dp.toPx()
                            translationY = 4.dp.toPx()
                        }
                        .background(
                            color = Color(0xFFf24cac),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(10.dp)
                ){
                    Column(
                        modifier = Modifier
                        ,
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = "Humedad del suelo",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.background
                        )
                        Text(
                            text = "${ hum.toString() } %",
                            fontWeight = FontWeight.Bold,
                            fontSize = 40.sp,
                            color = MaterialTheme.colorScheme.background
                        )
                    }

                }
            }
        }
        Row(
            modifier = Modifier
                .padding(vertical = 10.dp, horizontal = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                fontSize = 12.sp,
                fontWeight = FontWeight.Light,
                text = "Datos recopilados el: ${data.message[0].posted_at}"
            )
        }
    }


}

fun setProgressColor(hum: Int): Color {
    var color = Color(0xFFFFFFFF)
    if(hum.toFloat() >= 85){
        color = Color(0xFF00FF13)
    }else{
        if( 70 > hum.toFloat() && hum.toFloat() <= 50){
            color = Color(0xFFFFE100)
        }else{
            if(30 > hum.toFloat() && hum.toFloat() <= 10){
                color = Color(0xFFFF7600)
            }else{
                color = Color(0xFFFF2500)
            }
        }
    }
    return color
}
