package com.example.dsmdesafiopractico3gc180313mm200149

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dsmdesafiopractico3gc180313mm200149.db.HelperDB

class ListaListasCompras : AppCompatActivity() {

    lateinit var dbHelper: HelperDB
    lateinit var db: SQLiteDatabase

    //Datos
    var ids= mutableListOf<String>()
    var titulos= mutableListOf<String>()
    var fechas= mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lista_listas_compras)
        val recyclerViewListas = findViewById<RecyclerView>(R.id.recycleListas)?: return

        dbHelper = HelperDB(this)
        db = dbHelper.readableDatabase


        //Llenando listas mutbales
            val cursor3: Cursor? = db.rawQuery("SELECT id,titulo,fecha FROM ListaCompras", null)
            cursor3?.moveToFirst()
            // Iterar sobre los resultados y mostrarlos en println
            while (cursor3?.isAfterLast == false) {
                val idListaCompras = cursor3.getString(cursor3.getColumnIndexOrThrow("id"))
                val tituloLista = cursor3.getString(cursor3.getColumnIndexOrThrow("titulo"))
                val fechaLista = cursor3.getString(cursor3.getColumnIndexOrThrow("fecha"))
                ids.add(idListaCompras)
                titulos.add(tituloLista)
                fechas.add(fechaLista)
                cursor3.moveToNext()
            }
            //Recycle view para las listas
            val adapter = CustomAdapterListasCompras(ids,titulos,fechas)
            recyclerViewListas.layoutManager = LinearLayoutManager(this)
            recyclerViewListas.adapter = adapter

        }
    }
