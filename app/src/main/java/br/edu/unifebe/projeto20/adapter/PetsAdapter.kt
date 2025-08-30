package br.edu.unifebe.projeto20.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.edu.unifebe.projeto20.Model.Pet
import br.edu.unifebe.projeto20.databinding.PetsItemBinding
import com.bumptech.glide.Glide

class PetsAdapter(
    private val pets: List<Pet>,
    private val onItemClick: (Pet) -> Unit
) : RecyclerView.Adapter<PetsAdapter.PetsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetsViewHolder {
        val binding = PetsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PetsViewHolder(binding)
    }

    override fun getItemCount() = pets.size

    override fun onBindViewHolder(holder: PetsViewHolder, position: Int) {
        val item = pets[position]
        holder.bind(item)
    }

    inner class PetsViewHolder(private val binding: PetsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(pet: Pet) {
            binding.txtNome.text = pet.nome
            binding.txtDescricao.text = pet.descricao

            Glide.with(binding.root.context)
                .load(pet.imagemUrl)
                .into(binding.imgPets)

            binding.root.setOnClickListener {
                onItemClick(pet)
            }

            binding.btnInfo?.setOnClickListener {
                onItemClick(pet)
            }
        }
    }
}
