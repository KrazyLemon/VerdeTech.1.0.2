package com.krazylemon.verdetech102.pages.login

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.krazylemon.verdetech102.HomeActivity
import com.krazylemon.verdetech102.R
import com.krazylemon.verdetech102.api.dbOperations

@Composable
fun RegisterScreen(navController: NavController,context: Context){

    var db = dbOperations(context = context)

    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var nombre by remember{
        mutableStateOf("")
    }
    var equipo by remember {
        mutableStateOf("")
    }
    var error by remember {
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
            text = "Registra una Cuenta"
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
            value = nombre,
            onValueChange = {nombre = it},
            label = {
                Text(text = "Nombre Completo")
            }
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
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = password,
            onValueChange = { password = it},
            label = {
                Text(text = "Contraseña")
            },
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = equipo,
            onValueChange = { equipo = it },
            label = {
                Text(text = "Numero de Equipo")
            },
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = "¿Olvidaste tu contraseña?",
                modifier = Modifier.clickable {

                },
                color = MaterialTheme.colorScheme.secondary,
                textDecoration = TextDecoration.Underline
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = {
                if(nombre.isEmpty() || email.isEmpty() || password.isEmpty() || equipo.isEmpty()){
                    error = "Por favor, complete todos los campos"
                }else{
                    try{
                        val user = db.addUsuario(nombre,email,password,equipo)
                        if(user != -1L){
                            val intent = Intent(context, HomeActivity::class.java)
                            context.startActivity(intent)
                            //navController.navigate("home")
                        }else{
                            error = "Error al registrar usuario"
                        }
                    }catch ( e : Exception ){
                        error = "Error inesperado ${e.message.toString()}"
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ){
            Text(text = "Registrarse")
        }
        Spacer(modifier = Modifier.height(32.dp))
        Text(text = "¿Ya te has registrado?")
        Text(
            text = "Inicia Sesión",
            modifier = Modifier.clickable {
                navController.navigate("login")
            },
            color = MaterialTheme.colorScheme.secondary,
            textDecoration = TextDecoration.Underline
        )
    }
}