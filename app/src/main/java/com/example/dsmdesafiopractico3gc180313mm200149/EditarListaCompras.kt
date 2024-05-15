package com.example.dsmdesafiopractico3gc180313mm200149

import android.app.DatePickerDialog
import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dsmdesafiopractico3gc180313mm200149.db.HelperDB
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class EditarListaCompras: AppCompatActivity() {
    lateinit var dbHelper: HelperDB
    lateinit var db: SQLiteDatabase

    //  productos agregados  a la lista
    var ids = mutableListOf<String>()
    var productos= mutableListOf<String>()
    var idlistacompra = mutableListOf<String>()


    //Variables de datepicker
    lateinit var btnShowDataPicker: Button
    private val calendar = Calendar.getInstance()
    lateinit var tvDate: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.editar_lista)
        val recyclerViewListas = findViewById<RecyclerView>(R.id.recycleProductosEditar)?: return

        var arrowLeft: ImageButton

        arrowLeft = findViewById(R.id.btn_back)

        arrowLeft.setOnClickListener{
            val intent = Intent(this,ListaListasCompras::class.java)
            startActivity(intent)
        }


        var txtID = TextView(this)
        var txtTitulo = TextView(this)
        var btnShowDataPicker : Button
        var btnEditarTituloFecha: Button

        txtID = findViewById(R.id.txtID)
        txtTitulo= findViewById(R.id.txtTitulo)
        tvDate = findViewById(R.id.tvSelectDate)
        btnShowDataPicker = findViewById(R.id.btnShowDatePicker)
        btnEditarTituloFecha = findViewById(R.id.btnEditarLista)
        btnEditarTituloFecha.setOnClickListener{

            val dbHelper = HelperDB(this)
            val db = dbHelper.writableDatabase


            val valores = ContentValues().apply {
                put("titulo", txtTitulo.text.toString())
                put("fecha", tvDate.text.toString())
            }
            val filasActualizadas =
                db.update("ListaCompras", valores, "id = ?", arrayOf(txtID.text.toString()))

            if (filasActualizadas > 0) {
                Toast.makeText(this, "Lista actualizada con exito", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, ListaListasCompras::class.java)
                startActivity(intent)
            } else {
                println("Derrota")
            }
        }
        //DataPicker
        btnShowDataPicker = findViewById(R.id.btnShowDatePicker)
        btnShowDataPicker.setOnClickListener{
            showDataPicker()
        }
        val extras = intent.extras
        txtID.text = extras?.getString("id").toString()
        txtTitulo.text = extras?.getString("titulo").toString()
        tvDate.text = extras?.getString("fecha").toString()



        val dbHelper = HelperDB(this)
        val db = dbHelper.writableDatabase
        val cursor2: Cursor? = db.rawQuery(
            "SELECT id, nombre, idListaCompras FROM ProductosEnLista WHERE idListaCompras = ?", arrayOf(txtID.text.toString()))
        cursor2?.moveToFirst()
        while (cursor2?.isAfterLast == false) {
            val idproducto = cursor2.getString(cursor2.getColumnIndexOrThrow("id"))
            val nombreProducto = cursor2.getString(cursor2.getColumnIndexOrThrow("nombre"))
            val  idlistadecomprasproducto = cursor2.getString(cursor2.getColumnIndexOrThrow("idListaCompras"))
            ids.add(idproducto)
            productos.add(nombreProducto)
            idlistacompra.add(idlistadecomprasproducto)
            cursor2.moveToNext()
        }
        cursor2?.close()
        db.close()

        val adapter = CustomAdaperProductosEditar(ids, productos, idlistacompra)
        recyclerViewListas.layoutManager = LinearLayoutManager(this)
        recyclerViewListas.adapter = adapter

    }
    fun showDataPicker(){
        val datePickerDialog = DatePickerDialog(this,{DatePicker, year: Int, monthOfYear: Int, dayOfMonth:Int ->
            val selectedDate : Calendar = Calendar.getInstance()
            selectedDate.set(year,monthOfYear,dayOfMonth)
            val dataFormat = SimpleDateFormat("dd/MM/YYYY", Locale.getDefault())
            val formattedDate = dataFormat.format(selectedDate.time)
            tvDate.text = formattedDate
        },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }
}