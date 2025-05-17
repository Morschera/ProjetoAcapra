package br.edu.unifebe.projeto20.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.edu.unifebe.projeto20.Model.Pet
import br.edu.unifebe.projeto20.databinding.PetsItemBinding

class PetsAdapter (private val context: Context, private val petsList: MutableList<Pet>):
    RecyclerView.Adapter<PetsAdapter.PetsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetsViewHolder {
        val listItem = PetsItemBinding.inflate(LayoutInflater.from(context),parent,false)
        return PetsViewHolder(listItem)
    }

    override fun getItemCount() = petsList.size

    override fun onBindViewHolder(holder: PetsViewHolder, position: Int) {
        holder.imgPets.setBackgroundResource(petsList[position].imgPets)
        holder.nome.text = petsList[position].nome
        holder.descricao.text = petsList[position].descricao
    }

    inner class PetsViewHolder(binding: PetsItemBinding): RecyclerView.ViewHolder(binding.root){
        val imgPets = binding.imgPets
        val nome = binding.txtNome
        val descricao = binding.txtDescricao
    }
}