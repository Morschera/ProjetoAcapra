package br.edu.unifebe.projeto20.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.edu.unifebe.projeto20.Model.Formulario
import br.edu.unifebe.projeto20.R

class FormularioAdapter(
    private val lista: List<Formulario>,
    private val onItemClick: (Formulario) -> Unit
) : RecyclerView.Adapter<FormularioAdapter.FormularioViewHolder>() {

    class FormularioViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val nomeText: TextView = view.findViewById(R.id.tvNomeFormulario)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FormularioViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_formulario, parent, false)
        return FormularioViewHolder(view)
    }

    override fun onBindViewHolder(holder: FormularioViewHolder, position: Int) {
        val item = lista[position]
        holder.nomeText.text = item.nomeCompleto
        holder.view.setOnClickListener { onItemClick(item) }
    }

    override fun getItemCount(): Int = lista.size
}
