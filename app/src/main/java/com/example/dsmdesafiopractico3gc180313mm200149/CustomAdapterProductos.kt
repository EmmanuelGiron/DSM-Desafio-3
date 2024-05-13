package com.example.dsmdesafiopractico3gc180313mm200149

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapterProductos(private val productos: MutableList<String>): RecyclerView.Adapter<CustomAdapterProductos.ViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_producto,viewGroup,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.itemNombreProducto .text = productos[i]
    }

    override fun getItemCount(): Int {
        return productos.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var itemNombreProducto : TextView
        var itemEliminarProductoAgregado: ImageView


        init {
            itemNombreProducto = itemView.findViewById(R.id.txtProductoAgregado)
            itemEliminarProductoAgregado = itemView.findViewById(R.id.btnEliminarProductoAgregado)

            itemEliminarProductoAgregado.setOnClickListener{
                val elementoAEliminar = itemNombreProducto.text.toString()
                if (productos.contains(elementoAEliminar)) {
                    productos.remove(elementoAEliminar)
                    //println(productos)
                    notifyDataSetChanged()
                } else {
                    // El elemento no está en la lista, maneja este caso según tu lógica
                }
            }
        }
    }
}