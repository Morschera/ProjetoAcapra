package br.edu.unifebe.projeto20.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.edu.unifebe.projeto20.R

class FormAdapter(
    private val lista: List<String>,
    private val onClick: (String) -> Unit
) : RecyclerView.Adapter<FormAdapter.FormViewHolder>() {

    class FormViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtNome: TextView = itemView.findViewById(R.id.txtNome)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FormViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.pets_item, parent, false)
        return FormViewHolder(view)
    }

    override fun onBindViewHolder(holder: FormViewHolder, position: Int) {
        val nome = lista[position]
        holder.txtNome.text = nome

        holder.itemView.setOnClickListener {
            onClick(nome)
        }
    }

    override fun getItemCount(): Int = lista.size
}