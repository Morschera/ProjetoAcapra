package br.edu.unifebe.projeto20.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.edu.unifebe.projeto20.DetalhesPetActivity
import br.edu.unifebe.projeto20.Model.Pet
import br.edu.unifebe.projeto20.databinding.PetsItemBinding
import com.bumptech.glide.Glide

class PetsAdapter(
    private val context: Context,
    private val petsList: MutableList<Pet>
) : RecyclerView.Adapter<PetsAdapter.PetsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetsViewHolder {
        val listItem = PetsItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return PetsViewHolder(listItem)
    }

    override fun getItemCount() = petsList.size

    override fun onBindViewHolder(holder: PetsViewHolder, position: Int) {
        val pet = petsList[position]

        // Carregar imagem com Glide
        Glide.with(context)
            .load(pet.imagemUrl) // se for URL
            .into(holder.imgPets)

        holder.nome.text = pet.nome
        holder.descricao.text = pet.descricao

        // Clique no botão de info → abre tela de detalhes
        holder.btnInfo.setOnClickListener {
            val intent = Intent(context, DetalhesPetActivity::class.java)
            intent.putExtra("pet", pet)
            context.startActivity(intent)
        }
    }

    inner class PetsViewHolder(binding: PetsItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val imgPets = binding.imgPets
        val nome = binding.txtNome
        val descricao = binding.txtDescricao
        val btnInfo = binding.btnInfo
    }
}
