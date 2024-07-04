package com.krazylemon.verdetech102.pages.home

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.ImageView
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import com.krazylemon.verdetech102.ApiViewModel
import com.krazylemon.verdetech102.R
import com.krazylemon.verdetech102.api.DhtApi

@Composable
fun UsuarioPage(context: Context){
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .fillMaxHeight(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ){
            Text(
                text = "Soporte Técnico",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
            )
        }
        Text(
            text = "Bienvenido a la sección de soporte técnico. " +
                    "Aquí encontrarás información sobre cómo utilizar " +
                    "cada una de las funcionalidades de nuestra aplicación.",
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
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
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
                        showConfirmationDialog("https://www.facebook.com/roberto.castillo.796?locale=es_LA",context)
                    },
                painter = painterResource(id = R.drawable.fb_icon) ,
                contentDescription = "Fb_Icon",
            )
            Image(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .size(48.dp)
                    .clickable {
                        showConfirmationDialog("https://www.instagram.com/_manu_pay_/",context)
                    },
                painter = painterResource(id = R.drawable.ig_icon) ,
                contentDescription = "Ig_Icon"
            )
            Image(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .size(48.dp)
                    .clickable {
                        showConfirmationDialog("https://www.tiktok.com/@alanortiz387?lang=es",context)
                    },
                painter = painterResource(id = R.drawable.tik_tok_icon) ,
                contentDescription = "TikTok_Icon"
            )
            Image(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .size(48.dp)
                    .clickable {
                        showConfirmationDialog("https://wa.me/5633816972",context)
                    },
                painter = painterResource(id = R.drawable.whats_icon) ,
                contentDescription = "WhatsApp_Icon"
            )
            Image(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .size(48.dp)
                    .clickable {
                        showConfirmationDialog("mailto:203107151@cuautitlan.tecnm.mx",context)
                    },
                painter = painterResource(id = R.drawable.gmail_icon) ,
                contentDescription = "Mail_Icon"
            )
        }
    }
}

fun showConfirmationDialog(url: String,context: Context){
    val builder = AlertDialog.Builder(context)
    builder.setMessage("¿Estás seguro de que quieres abrir este enlace?")
        .setPositiveButton("Sí") { _, _ ->
            //openUrl(url)
        }
        .setNegativeButton("No", null)
    builder.create().show()
}

@Composable
fun openUrl(url: String) {
    val context = LocalContext.current
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    context.startActivity(intent)
}
