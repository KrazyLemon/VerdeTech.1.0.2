package com.krazylemon.verdetech102.api

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class dbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "verdetech.db"
        const val DATABASE_VERSION = 1

        const val TABLE_USUARIOS = "usuariostech"
        const val COLUMN_ID = "id"
        const val COLUMN_NOMBRE_COMPLETO = "nombre_completo"
        const val COLUMN_CORREO_ELECTRONICO = "correo_electronico"
        const val COLUMN_CONTRASENA = "contrasena"
        const val COLUMN_EQUIPO = "Equipo"
    }

    private val SQL_CREATE_ENTRIES =
        "CREATE TABLE $TABLE_USUARIOS (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_NOMBRE_COMPLETO TEXT," +
                "$COLUMN_CORREO_ELECTRONICO TEXT," +
                "$COLUMN_CONTRASENA TEXT," +
                "$COLUMN_EQUIPO TEXT);"


    private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS $TABLE_USUARIOS"

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

}
