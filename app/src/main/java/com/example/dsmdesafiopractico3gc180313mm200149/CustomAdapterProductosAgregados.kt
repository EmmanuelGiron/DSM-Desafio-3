package com.example.dsmdesafiopractico3gc180313mm200149

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapterProductosAgregados(private val productos: MutableList<String>): RecyclerView.Adapter<CustomAdapterProductosAgregados.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_productos_agregados,viewGroup,false)
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
        init {
            itemNombreProducto = itemView.findViewById(R.id.txtProductoAgregadoCardID)
        }
    }

}
