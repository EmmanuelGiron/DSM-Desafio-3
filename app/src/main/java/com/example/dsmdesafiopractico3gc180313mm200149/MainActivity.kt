package com.example.dsmdesafiopractico3gc180313mm200149

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dsmdesafiopractico3gc180313mm200149.db.HelperDB
//import com.example.dsmdesafiopractico3gc180313mm200149.model.ListaCompras
//import com.example.dsmdesafiopractico3gc180313mm200149.model.ProductosEnLista
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.random.Random
import android.content.Context
import android.content.Intent


//import com.example.dsmdesafiopractico3gc180313mm200149.utils.MyDialog
//, View.OnClickListener
class MainActivity : AppCompatActivity() {



    var productosAgregados= mutableListOf<String>()

    //Datapicker
    lateinit var tvDate: TextView
    lateinit var btnShowDataPicker: Button
    private val calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView = findViewById<RecyclerView>(R.id.recycleProductos)?: return

        var arrowLeft: ImageButton

        arrowLeft = findViewById(R.id.btn_back)

        arrowLeft.setOnClickListener{
            val intent = Intent(this,ListaListasCompras::class.java)
            startActivity(intent)
        }

        //Datos
        var txtTitulo : EditText
        var txtProducto: EditText
        var btnAgregarProducto: Button
        var btnAgregarListaCompras: Button

        txtTitulo = findViewById(R.id.txtTitulo)
        txtProducto = findViewById(R.id.txtProducto)
        btnAgregarProducto = findViewById(R.id.btbAgrerProducto)
        btnAgregarListaCompras = findViewById(R.id.btnCrearLista)

        //DataPicker
        tvDate = findViewById(R.id.tvSelectDate)
        btnShowDataPicker = findViewById(R.id.btnShowDatePicker)
        btnShowDataPicker.setOnClickListener{
            showDataPicker()
        }
        btnAgregarListaCompras.setOnClickListener{
            val dbHelper = HelperDB(this)
            val db = dbHelper.writableDatabase


            val valoresTablaListaCompras = ContentValues().apply {
                put("titulo",txtTitulo.text.toString() )
                put("fecha", tvDate.text.toString())
            }
            val newRowId = db?.insert("ListaCompras", null, valoresTablaListaCompras)

            if (newRowId != -1L) {
                productosAgregados.forEachIndexed { index, elementoActual ->
                    val valoresTablaProductosEnLista = ContentValues().apply {
                        put("nombre",elementoActual)
                        put("idListaCompras", newRowId)
                    }
                    db?.insert("ProductosEnLista", null, valoresTablaProductosEnLista)
                    val intent = Intent(this, ListaListasCompras::class.java)
                    startActivity(intent)
                }

            } else {
                println("Error al insertar el registro")
            }

        }

        //Agregar producto a la lista mutable
        btnAgregarProducto.setOnClickListener{
            productosAgregados.add(txtProducto.text.toString())
            txtProducto.setText("")
            //println(productosAgregados)
            val adapter = CustomAdapterProductos(productosAgregados)
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = adapter
        }


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