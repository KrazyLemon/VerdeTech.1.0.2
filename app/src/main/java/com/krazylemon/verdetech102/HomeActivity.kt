package com.krazylemon.verdetech102

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.krazylemon.verdetech102.pages.home.MainScreen
import com.krazylemon.verdetech102.ui.theme.VerdeTech102Theme

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val viewModel = ViewModelProvider(this)[ApiViewModel::class.java]
        val dataViewModel = ViewModelProvider(this)[ApiViewModel::class.java]
        super.onCreate(savedInstanceState)
        setContent {
            VerdeTech102Theme {
                MainScreen(ApiViewModel = viewModel, dataViewModel = dataViewModel, context = this)
            }
        }
    }

    override fun onBackPressed() {
        showLogoutConfirmationDialog()
    }

    private fun showLogoutConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Cerrar Sesión")
        builder.setMessage("¿Estás seguro de que deseas cerrar sesión?")
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
        Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
