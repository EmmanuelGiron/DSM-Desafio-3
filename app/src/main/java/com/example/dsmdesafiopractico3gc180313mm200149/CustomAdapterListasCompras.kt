package com.example.dsmdesafiopractico3gc180313mm200149

import android.app.DatePickerDialog
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dsmdesafiopractico3gc180313mm200149.db.HelperDB
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class CustomAdapterListasCompras(private val ids: MutableList<String>,private val titulos: MutableList<String>,private val fechas: MutableList<String>): RecyclerView.Adapter<CustomAdapterListasCompras.ViewHolder>() {
    //lateinit var dbHelper: HelperDB
    //lateinit var db: SQLiteDatabase

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_lista_compras,viewGroup,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.itemIDListaCompras.text = ids[i]
        viewHolder.itemTituloLista.text = titulos[i]
        viewHolder.itemFechaEntrega.text = fechas[i]

        val dbHelper = HelperDB(viewHolder.itemView.context)
        val db = dbHelper.readableDatabase

        val cursor2: Cursor? = db.rawQuery(
            "SELECT idListaCompras, nombre FROM ProductosEnLista WHERE idListaCompras = ?",
            arrayOf(viewHolder.itemIDListaCompras.text.toString())
        )
        cursor2?.moveToFirst()
        while (cursor2?.isAfterLast == false) {
            val nombreProducto = cursor2.getString(cursor2.getColumnIndexOrThrow("nombre"))
            viewHolder.itemProdutosAgregados.add(nombreProducto)
            cursor2.moveToNext()
        }
        cursor2?.close()
        db.close()

        // Crear el adaptador después de agregar todos los elementos a la lista
        val adapter = CustomAdapterProductosAgregados(viewHolder.itemProdutosAgregados)
        viewHolder.itemRecyclerViewProductos.layoutManager = LinearLayoutManager(viewHolder.itemView.context)
        viewHolder.itemRecyclerViewProductos.adapter = adapter

        // Notificar al adaptador que los datos han cambiado
        adapter.notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return ids.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var itemIDListaCompras: TextView
        var itemTituloLista : TextView
        var itemFechaEntrega: TextView
        var itemRecyclerViewProductos: RecyclerView
        var itemBtnEditar : Button

        var itemProdutosAgregados = mutableListOf<String>()



        init {
            itemIDListaCompras = itemView.findViewById(R.id.IDCardID)
            itemTituloLista = itemView.findViewById(R.id.tituloCardID)
            itemFechaEntrega = itemView.findViewById(R.id.fechaCardID)
            itemBtnEditar = itemView.findViewById(R.id.btnEditar)

            itemRecyclerViewProductos = itemView.findViewById(R.id.productosEnListaCardID)

            //Abrir vista para actualizacion de datos
            itemBtnEditar.setOnClickListener{
                val intent = Intent(itemBtnEditar.context,EditarListaCompras::class.java)
                intent.putExtra("id",itemIDListaCompras.text.toString())
                intent.putExtra("titulo",itemTituloLista.text.toString())
                intent.putExtra("fecha",itemFechaEntrega.text.toString())
                itemBtnEditar.context.startActivity(intent)
            }


                /*
                val cursor2: Cursor? = db.rawQuery(
                    "SELECT idListaCompras, nombre FROM ProductosEnLista WHERE idListaCompras = ?", arrayOf(itemIDListaCompras.text.toString()))
                cursor2?.moveToFirst()
                while (cursor2?.isAfterLast == false) {
                    val nombreProducto = cursor2.getString(cursor2.getColumnIndexOrThrow("nombre"))
                    itemProdutosAgregados.add(nombreProducto)
                    cursor2.moveToNext()
                }
                cursor2?.close()
                 db.close()
                val adapter = CustomAdapterProductosAgregados(itemProdutosAgregados)
                itemRecyclerViewProductos.layoutManager = LinearLayoutManager(itemView.context)
                itemRecyclerViewProductos.adapter = adapter*/

            }
        }

    }

