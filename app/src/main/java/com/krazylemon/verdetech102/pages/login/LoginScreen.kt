package com.krazylemon.verdetech102.pages.login

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
import com.krazylemon.verdetech102.MainActivity
import com.krazylemon.verdetech102.R
import com.krazylemon.verdetech102.api.dbOperations

@Composable
fun LoginScreen(
    navController: NavController,
    context: MainActivity
){
    var db = dbOperations(context = context)

    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
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
            fontSize = 28.sp, fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Ingresa a tu cuenta"
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = error,
            color = MaterialTheme.colorScheme.error,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(32.dp))
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
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = "¿Olvidaste Tu Contraseña?",
                modifier = Modifier.clickable {
                    navController.navigate("pass")
                },
                color = MaterialTheme.colorScheme.secondary,
                textDecoration = TextDecoration.Underline
            )
        }
        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                if(email.isEmpty() || password.isEmpty()) {
                    error = "Por favor, complete todos los campos"
                } else {
                    try{
                        val user = db.getUsuario(email,password)
                        if(user !=null){
                            navController.navigate("home")
                        }else{
                            error = "Correo electronico ó Contraseña incorrecta"
                        }
                    }catch (ex : Exception){
                        error = "Error al iniciar sesion reinicie la app para continuar"
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ){
            Text(text = "Ingresar")
        }
        Spacer(modifier = Modifier.height(4.dp))

        Button(
            onClick = {
                navController.navigate("registro")
            },
            modifier = Modifier.fillMaxWidth()
            ){
            Text(text = "Registrarse")
        }
    }


}