package com.krazylemon.verdetech102.pages.home

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FilledIconButton
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
import com.krazylemon.verdetech102.api.OutputResponse
import com.krazylemon.verdetech102.models.OutputList
import com.krazylemon.verdetech102.models.UpdatedModel

var autopump = false
val action = "output_limit"

@Composable
fun HomeScreen(viewModel: ApiViewModel, context: Context){

    val modifier = Modifier
    val DhtResult = viewModel.DhtResult.observeAsState()
    val OutputsResult = viewModel.OutputResult.observeAsState()
    val UpdateResult = viewModel.UpdateResult.observeAsState()


    LaunchedEffect(Unit) {
        while (true) {
            viewModel.getData(action,1)
            delay(60000) // 60000 ms = 1 minuto
        }
    }

    LaunchedEffect(Unit) {
        viewModel.getOutputsState()
    }

    HomeHeader(modifier,viewModel)
    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

    ) {
        when(val result = DhtResult.value){
            is NetworkResponse.Error -> {
                Column(
                    modifier = modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(text = result.message)
                }
            }
            NetworkResponse.Loading -> {
                Column(
                    modifier = modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    CircularProgressIndicator()
                }
            }
            is NetworkResponse.Success -> {
                DhtDetails(modifier,viewModel, result.data)
            }
            null -> {}
        }
        when(val result = OutputsResult.value){
            is OutputResponse.Error ->{
                Text(text = result.message)
            }
            OutputResponse.Loading ->{
                CircularProgressIndicator()
            }
            is OutputResponse.Success ->{
                BombaButton(modifier,viewModel,result.data)
                //var bombaList = result.data
            }
            null->{}
        }
        when(val result = UpdateResult.value ){
            is OutputResponse.Error ->{
                Text(text = result.message)
            }
            OutputResponse.Loading ->{
                //CircularProgressIndicator()
            }
            is OutputResponse.Success ->{
                UpdateBomba(result.data)
                //var bombaList = result.data
            }
            null->{}
        }
    }
}

@Composable
fun UpdateBomba(data : UpdatedModel) {
    Row{
        Text(
            text = "${data.message}"
        )
    }
}

@Composable
fun BombaButton(modifier: Modifier,viewModel : ApiViewModel,data: OutputList) {
    var puerto = false
    if(data.message[1].state != "1") puerto = true else puerto = false
    var bombaState by remember { mutableStateOf(puerto) }
    Row (
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            text = "Bomba",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = modifier
                .padding(end = 4.dp)
        )
        Text(
            if(!bombaState) "(Desactivada)" else "(Activada)",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            modifier = modifier
                .padding(end = 4.dp)
        )
        Switch(
            checked = bombaState,
            onCheckedChange = {
                if (bombaState) viewModel.updateState(1)
                else viewModel.updateState(0)
                bombaState = it
            },
            // Se deshabilita si esta en automode = True
            enabled = !autopump
        )
    }
}

@Composable
fun DhtDetails(modifier:Modifier,viewModel : ApiViewModel,data : DhtModel){
    var a = data.message[0].smp_a.toInt()
    var b = data.message[0].smp_b.toInt()
    var c = data.message[0].smp_c.toInt()
    var d = data.message[0].smp_d.toInt()
    var e = data.message[0].smp_e.toInt()

    var prom = (a + b + c + d + e) / 5
    var res = ( prom * 100 ) / 4095
    var hum = 100 - res
    var estado = setState(hum)

    var tip by remember {
        mutableStateOf("")
    }

    autopump = autopump(hum)

    if(autopump(hum)){
        tip = "Inicio de Riego Automatico: Humedad Baja"
        viewModel.updateState(0)
    }else{
        tip = "Humedad del suelo buena"
        viewModel.updateState(1)
    }


    Column(
        modifier = modifier
            .padding(4.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = modifier.height(80.dp))
        Row(
            modifier = modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = "Estado General",
                fontSize = 32.sp,
                lineHeight = 32.sp,
                fontWeight = FontWeight.SemiBold,

            )
        }
        //********** Circular Progress Indicator ****************//
        Box(
            modifier = modifier
                .padding(16.dp)
                .size(180.dp),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = estado,
                fontSize = 20.sp,
                fontWeight = FontWeight.Light
            )
            CircularProgressIndicator(
                progress = { hum.toFloat() },
                modifier = modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                color = setProgressColor(hum),
                strokeWidth = 16.dp,
            )
        }
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = (tip),
                color = if (autopump(hum)) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
            )
        }
        Row(
            modifier = modifier
                .fillMaxWidth(),
                //.padding(horizontal = 30.dp),
            horizontalArrangement = Arrangement.Center
        ){
            Box(
                modifier = modifier
                    .padding(4.dp)
            ){
                Box(
                    modifier = modifier
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
                        modifier = modifier,
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
                            text = "${ data.message[0].temp.toFloat().toInt() }°C",
                            fontWeight = FontWeight.Bold,
                            fontSize = 40.sp,
                            color = MaterialTheme.colorScheme.background
                        )
                    }

                }
            }
            Box(
                modifier = modifier
                    .padding(4.dp)
            ){
                Box(
                    modifier = modifier
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
                        modifier = modifier,
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
                            text = "${ data.message[0].hum.toFloat().toInt() }%",
                            fontWeight = FontWeight.Bold,
                            fontSize = 40.sp,
                            color = MaterialTheme.colorScheme.background
                        )
                    }

                }
            }
        }
        Row(
            modifier = modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ){
            Box(
                modifier = modifier
                    .padding(4.dp)
            ){
                Box(
                    modifier = modifier
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
                        modifier = modifier
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
                            text = "${ data.message[0].heat.toFloat().toInt() }J",
                            fontWeight = FontWeight.Bold,
                            fontSize = 40.sp,
                            color = MaterialTheme.colorScheme.background
                        )
                    }

                }
            }
            Box(
                modifier = modifier
                    .padding(4.dp)
            ){
                Box(
                    modifier = modifier
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
                        modifier = modifier,
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
                            text = "${hum}%",
                            fontWeight = FontWeight.Bold,
                            fontSize = 40.sp,
                            color = MaterialTheme.colorScheme.background
                        )
                    }

                }
            }
        }
        Row(
            modifier = modifier
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

fun autopump(hum: Int): Boolean {
    if(hum > 85 ){
        return false
    }else{
        if(hum in 51..85){
            return false
        }else{
            if(hum in 31..50){
                return true
            }else{
                return true
            }
        }
    }
}
fun setProgressColor(hum: Int): Color {
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
fun setState(hum: Int):String{
    if(hum > 85 ){
        return "Excelente"
    }else{
        if(hum in 51..85){
            return "Bueno"
        }else{
            if(hum in 31..50){
                return "Regular"
            }else{
                return "Malo"
            }
        }
    }
}

@Composable
fun HomeHeader(modifier: Modifier,viewModel: ApiViewModel){
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
    ){
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = "Inicio",
                modifier = Modifier.padding(16.dp),
                color = MaterialTheme.colorScheme.background,
                fontSize = 22.sp
            )
            FilledIconButton(
                onClick = { viewModel.getData(action,1) },
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_refresh),
                    contentDescription = "RefreshIcon",
                    tint = Color.White
                )
            }
        }
    }
}