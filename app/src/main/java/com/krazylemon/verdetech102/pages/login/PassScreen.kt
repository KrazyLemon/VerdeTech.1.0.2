package com.krazylemon.verdetech102.pages.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.krazylemon.verdetech102.MainActivity
import com.krazylemon.verdetech102.R
import com.krazylemon.verdetech102.api.dbOperations


@Composable
fun PassScreen(
    navController: NavController,
    context: MainActivity
){
    var db = dbOperations(context = context)

    var email by remember {
        mutableStateOf("")
    }
    var error by remember {
        mutableStateOf("")
    }
    var passnueva by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Login Image",
            modifier = Modifier.size(250.dp))
        Text(
            text = "Invernadero Inteligente",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Restablece tu contraseña"
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = error,
            color = MaterialTheme.colorScheme.error,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = email,
            onValueChange = {email = it},
            label = {
                Text(text = "Correo Electronico")
            }
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = {
                if(email.isEmpty() ){
                    error = "Por favor, ingrese su correo electrónico"
                }else{
                    try{
                        val user = db.getUsuarioByEmail(email)
                        if(user != null){
                            val newpass = generarContrasenaAleatoria()
                            val isUpdated = db.updateContrasena(email,newpass)
                            if(isUpdated){
                                passnueva = "Contraseña actualizada: ${ newpass }"
                            }else{
                                error = "Algo salió mal"
                            }
                        }else{
                            error = "No se encontro su correo electrónico"
                        }
                    }catch ( e : Exception ){
                        error = "Error inesperado ${e.message.toString()}"
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ){
            Text(text = "Cambiar Contraseña")
        }

        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = passnueva,
            color = MaterialTheme.colorScheme.tertiary,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
private fun generarContrasenaAleatoria(): String {
    val caracteresPermitidos = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
    return (1..8)
        .map { caracteresPermitidos.random() }
        .joinToString("")
}