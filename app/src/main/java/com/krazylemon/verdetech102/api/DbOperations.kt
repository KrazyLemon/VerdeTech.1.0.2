package com.krazylemon.verdetech102.api

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log


class dbOperations(context: Context) {
    private val dbh = dbHelper(context)

    fun addUsuario(nombreCompleto: String, correoElectronico: String, contrasena: String,equipo: String): Long {
        val db: SQLiteDatabase = dbh.writableDatabase
        return try {
            val values = ContentValues().apply {
                put(dbHelper.COLUMN_NOMBRE_COMPLETO, nombreCompleto)
                put(dbHelper.COLUMN_CORREO_ELECTRONICO, correoElectronico)
                put(dbHelper.COLUMN_CONTRASENA, contrasena)
                put(dbHelper.COLUMN_EQUIPO, equipo)
            }

            val newRowId = db.insert(dbHelper.TABLE_USUARIOS, null, values)
            newRowId
        } catch (e: Exception) {
            Log.e("UsuarioOperations", "Error al insertar usuario", e)
            -1L
        } finally {
            db.close()
        }
    }

    fun getUsuarioByEmail(correoElectronico: String): Usuario? {
        val db: SQLiteDatabase = dbh.readableDatabase
        var usuario: Usuario? = null
        val cursor: Cursor = db.query(
            dbHelper.TABLE_USUARIOS,
            null,
            "${dbHelper.COLUMN_CORREO_ELECTRONICO} = ?",
            arrayOf(correoElectronico),
            null, null, null
        )

        with(cursor) {
            if (moveToFirst()) {
                usuario = Usuario(
                    getLong(getColumnIndexOrThrow(dbHelper.COLUMN_ID)),
                    getString(getColumnIndexOrThrow(dbHelper.COLUMN_NOMBRE_COMPLETO)),
                    getString(getColumnIndexOrThrow(dbHelper.COLUMN_CORREO_ELECTRONICO)),
                    getString(getColumnIndexOrThrow(dbHelper.COLUMN_CONTRASENA)),
                    getString(getColumnIndexOrThrow(dbHelper.COLUMN_EQUIPO))
                )
            } else {
                Log.d("UsuarioOperations", "Usuario no encontrado")
            }
        }
        cursor.close()
        db.close()
        return usuario
    }

    fun getUsuario(correoElectronico: String, contrasena: String): Usuario? {
        val db: SQLiteDatabase = dbh.readableDatabase
        var usuario: Usuario? = null
        val cursor: Cursor = db.query(
            dbHelper.TABLE_USUARIOS,
            null,
            "${dbHelper.COLUMN_CORREO_ELECTRONICO} = ? AND ${dbHelper.COLUMN_CONTRASENA} = ?",
            arrayOf(correoElectronico, contrasena),
            null, null, null
        )

        with(cursor) {
            if (moveToFirst()) {
                usuario = Usuario(
                    getLong(getColumnIndexOrThrow(dbHelper.COLUMN_ID)),
                    getString(getColumnIndexOrThrow(dbHelper.COLUMN_NOMBRE_COMPLETO)),
                    getString(getColumnIndexOrThrow(dbHelper.COLUMN_CORREO_ELECTRONICO)),
                    getString(getColumnIndexOrThrow(dbHelper.COLUMN_CONTRASENA)),
                    getString(getColumnIndexOrThrow(dbHelper.COLUMN_EQUIPO))
                )
            } else {
                Log.d("UsuarioOperations", "Usuario no encontrado")
            }
        }
        cursor.close()
        db.close()
        return usuario
    }

    fun getAllUsuarios(): List<Usuario> {
        val usuarios = mutableListOf<Usuario>()
        val db = dbh.readableDatabase
        val cursor: Cursor = db.query(
            dbHelper.TABLE_USUARIOS,
            null, null, null, null, null, null
        )

        with(cursor) {
            while (moveToNext()) {
                val usuario = Usuario(
                    getLong(getColumnIndexOrThrow(dbHelper.COLUMN_ID)),
                    getString(getColumnIndexOrThrow(dbHelper.COLUMN_NOMBRE_COMPLETO)),
                    getString(getColumnIndexOrThrow(dbHelper.COLUMN_CORREO_ELECTRONICO)),
                    getString(getColumnIndexOrThrow(dbHelper.COLUMN_CONTRASENA)),
                    getString(getColumnIndexOrThrow(dbHelper.COLUMN_EQUIPO))
                )
                usuarios.add(usuario)
            }
        }
        cursor.close()
        db.close()
        return usuarios
    }

    fun updateContrasena(correoElectronico: String, nuevaContrasena: String): Boolean {
        val db: SQLiteDatabase = dbh.writableDatabase
        return try {
            val values = ContentValues().apply {
                put(dbHelper.COLUMN_CONTRASENA, nuevaContrasena)
            }

            val rowsAffected = db.update(
                dbHelper.TABLE_USUARIOS,
                values,
                "${dbHelper.COLUMN_CORREO_ELECTRONICO} = ?",
                arrayOf(correoElectronico)
            )
            rowsAffected > 0
        } catch (e: Exception) {
            Log.e("UsuarioOperations", "Error al actualizar contraseña", e)
            false
        } finally {
            db.close()
        }
    }

    data class Usuario(
        val id: Long,
        val nombreCompleto: String,
        val correoElectronico: String,
        val contrasena: String,
        val equipo: String
    )
}
