package com.example.dsmdesafiopractico3gc180313mm200149

import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.dsmdesafiopractico3gc180313mm200149.db.HelperDB

class CustomAdaperProductosEditar(
    private val ids: MutableList<String>,
    private val productos: MutableList<String>,
    private val idlistacompras: MutableList<String>
) : RecyclerView.Adapter<CustomAdaperProductosEditar.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_producto_editar, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.itemIDProducto.text = ids[i]
        viewHolder.itemNombreProducto.text = productos[i]
        viewHolder.itemIDListaDeCompras.text = idlistacompras[i]
    }

    override fun getItemCount(): Int {
        return productos.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemIDProducto: TextView = itemView.findViewById(R.id.IDCardProductoEnLaLista)
        var itemNombreProducto: TextView = itemView.findViewById(R.id.txtProductoAgregado)
        var itemIDListaDeCompras: TextView = itemView.findViewById(R.id.IDCardListaDeCompras)
        var itemEliminarProductoAgregado: ImageView =
            itemView.findViewById(R.id.btnEliminarProductoAgregado)
        var itemEditarProductoAgregado: ImageView =
            itemView.findViewById(R.id.btnEditarProductoAgregado)
        var itemEditado: TextView = itemView.findViewById(R.id.editado)
        var itemEliminado: TextView = itemView.findViewById(R.id.eliminado)

        init {
            itemEditarProductoAgregado.setOnClickListener {
                showEditTextDialog(itemView.context, itemIDProducto.text.toString(),itemEditarProductoAgregado, itemEliminarProductoAgregado, itemEditado)
            }
            itemEliminarProductoAgregado.setOnClickListener {
                val dbHelper = HelperDB(itemView.context)
                val db = dbHelper.writableDatabase
                val idProducto = itemIDProducto.text.toString()

                // Mensaje de depuración para verificar el ID
                println("Intentando eliminar producto con ID: $idProducto")

                val filasEliminadas =
                    db.delete("ProductosEnLista", "id = ?", arrayOf(idProducto.toString()))

                if (filasEliminadas > 0) {
                    Toast.makeText(itemView.context, "Éxito: Se eliminó el producto con : ${itemNombreProducto.text.toString()}", Toast.LENGTH_SHORT).show()
                    itemEliminarProductoAgregado.visibility = View.GONE
                    itemEditarProductoAgregado.visibility = View.GONE
                    itemEliminado.visibility = View.VISIBLE
                } else {
                    println("Error: No se pudo eliminar el producto con ID: $idProducto")
                }
                db.close()
            }
        }

        private fun showEditTextDialog(context: Context, itemID: String,btnEditar: ImageView, btnEliminar: ImageView, editado: TextView) {
            val builder = AlertDialog.Builder(context)
            val inflater = LayoutInflater.from(context)
            val dialogLayout = inflater.inflate(R.layout.edit_text_layout, null)
            val editText = dialogLayout.findViewById<EditText>(R.id.et_editText)

            with(builder) {
                setTitle("Edición de producto")
                setPositiveButton("OK") { dialog, which ->
                    println(editText.text)
                    println(itemID)
                    val dbHelper = HelperDB(context)
                    val db = dbHelper.writableDatabase
                    val nuevoProducto = editText.text.toString()
                    val id = itemID
                    val valores = ContentValues().apply {
                        put("nombre", nuevoProducto)
                    }
                    val filasActualizadas =
                        db.update("ProductosEnLista", valores, "id = ?", arrayOf(id.toString()))

                    if (filasActualizadas > 0) {
                        Toast.makeText(itemView.context, "Éxito: Se actualizo el registro", Toast.LENGTH_SHORT).show()
                        btnEditar.visibility = View.GONE
                        btnEliminar.visibility = View.GONE
                        editado.visibility = View.VISIBLE
                    } else {
                        println("Derrota")
                    }
                    db.close()
                }
                setNegativeButton("Cancel") { dialog, which ->
                    Log.d("Main", "Negative button clicked")
                }
                setView(dialogLayout)
                show()
            }
        }
    }}