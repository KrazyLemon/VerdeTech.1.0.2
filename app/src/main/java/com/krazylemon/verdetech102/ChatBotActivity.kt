package com.krazylemon.verdetech102

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.krazylemon.verdetech102.pages.home.ChatbotScreen

import com.krazylemon.verdetech102.ui.theme.VerdeTech102Theme

class ChatBotActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val viewModel = ViewModelProvider(this)[ApiViewModel::class.java]
        setContent {
            VerdeTech102Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) {innerPadding ->
                    ChatbotScreen(Modifier.padding(innerPadding), viewModel)
                }
            }
        }
    }
    override fun onBackPressed() {
        showLogoutConfirmationDialog()
    }

    private fun showLogoutConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Cerrar Sesión")
        builder.setMessage("¿Estás seguro de que deseas Salir? Se perderá la Conversación")
        builder.setPositiveButton("Sí") { dialog, _ ->
            logout()
            dialog.dismiss()
        }
        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun logout() {
        Toast.makeText(this, "Chat finalizado", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, HomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}