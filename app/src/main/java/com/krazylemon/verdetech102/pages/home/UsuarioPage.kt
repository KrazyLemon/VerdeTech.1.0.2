package com.krazylemon.verdetech102.pages.home

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.krazylemon.verdetech102.R



@Composable
fun UsuarioPage(context: Context){
    UsuarioHeader()
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .fillMaxHeight(),
    ) {
        Spacer(modifier = Modifier.height(50.dp))
        Text(
            text = "Bienvenido a la sección de soporte técnico. " ,
            fontWeight = FontWeight.Thin,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Registar Usuario",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        Text(
            text = "Para registrar un nuevo usuario, ingrese el nombre completo, " +
                    "correo electrónico y contraseña. " +
                    "Luego, haga clic en el botón 'Registrar'.",
            fontWeight = FontWeight.Thin,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Recuperar Contraseña",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        Text(
            text = "Para recuperar su contraseña, ingrese su correo electrónico " +
                    "y haga clic en 'Recuperar Contraseña'. Se generará una nueva " +
                    "contraseña para usted.",
            fontWeight = FontWeight.Thin,
            fontSize = 18.sp
        )
        Text(
            text = "Ver Datos",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        Text(
            text = "Para ver los datos de su sistema, una vez que entre a la app " +
                    "y no se vean los datos, oprima el botón en la barra superior " +
                    "pegado a la derecha. Esto cargará la información de su sistema.",
                    fontWeight = FontWeight.Thin,
            fontSize = 18.sp
        )
        Text(
            text = "Ver Datos historicos",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        Text(
            text = "Para ver los datos de su sistema, una vez que entre a la app, " +
                    "vaya a la página Datos y oprima el botón en la barra superior. " +
                    "Elija un rango de fechas de las cuales quiere obtener los datos, " +
                    "dé click en continuar y verá sus lecturas de datos.",
                    fontWeight = FontWeight.Thin,
            fontSize = 18.sp
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ){
            Text(
                text = "Nuestras Redes",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
            )
        }
        Row(
            Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.Center,
        ){
            Image(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .size(48.dp)
                    .clickable {
                        showConfirmationDialog(
                            "https://www.facebook.com/roberto.castillo.796?locale=es_LA",
                            context
                        )
                    },
                painter = painterResource(id = R.drawable.fb_icon) ,
                contentDescription = "Fb_Icon",
            )
            Image(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .size(48.dp)
                    .clickable {
                        showConfirmationDialog("https://www.instagram.com/_manu_pay_/", context)
                    },
                painter = painterResource(id = R.drawable.ig_icon) ,
                contentDescription = "Ig_Icon"
            )
            Image(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .size(48.dp)
                    .clickable {
                        showConfirmationDialog(
                            "https://www.tiktok.com/@alanortiz387?lang=es",
                            context
                        )
                    },
                painter = painterResource(id = R.drawable.tik_tok_icon) ,
                contentDescription = "TikTok_Icon"
            )
            Image(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .size(48.dp)
                    .clickable {
                        showConfirmationDialog("https://wa.me/5633816972", context)
                    },
                painter = painterResource(id = R.drawable.whats_icon) ,
                contentDescription = "WhatsApp_Icon"
            )
            Image(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .size(48.dp)
                    .clickable {
                        showConfirmationDialog("mailto:203107151@cuautitlan.tecnm.mx", context)
                    },
                painter = painterResource(id = R.drawable.gmail_icon) ,
                contentDescription = "Mail_Icon"
            )
        }
    }
}

@Composable
fun UsuarioHeader(){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
    ){
        Text(
            text = "Soporte Tecnico",
            modifier = Modifier.padding(16.dp),
            color = MaterialTheme.colorScheme.background,
            fontSize = 22.sp
        )
    }
}

fun showConfirmationDialog(url: String,context: Context){
    val builder = AlertDialog.Builder(context)
    builder.setMessage("¿Estás seguro de que quieres abrir este enlace?")
        .setPositiveButton("Sí") { _, _ ->
            openUrl(url,context)
        }
        .setNegativeButton("No", null)
    builder.create().show()
}
fun openUrl(url: String, context: Context) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    context.startActivity(intent)
}
