package com.example.dsmdesafiopractico3gc180313mm200149.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class HelperDB(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "comprados.db"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE IF NOT EXISTS ListaCompras (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "titulo TEXT," +
                    "fecha TEXT)"
        )

        // Crear otra tabla
        db.execSQL(
            "CREATE TABLE IF NOT EXISTS ProductosEnLista (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nombre TEXT," +
                    "idListaCompras INTEGER," +  // Columna para la clave foránea
                    "FOREIGN KEY(idListaCompras) REFERENCES ListaCompras(id))"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Aquí puedes realizar operaciones de actualización de la base de datos si es necesario
        // Por ejemplo, eliminar tablas antiguas, agregar nuevas, etc.
    }
}