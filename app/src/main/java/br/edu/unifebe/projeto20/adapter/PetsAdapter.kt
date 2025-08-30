import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.edu.unifebe.projeto20.Model.Pet
import br.edu.unifebe.projeto20.databinding.PetsItemBinding
import com.bumptech.glide.Glide

class PetsAdapter(
    private val petsList: MutableList<Pet>
) : RecyclerView.Adapter<PetsAdapter.PetsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetsViewHolder {
        val binding = PetsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PetsViewHolder(binding)
    }

    override fun getItemCount() = petsList.size

    override fun onBindViewHolder(holder: PetsViewHolder, position: Int) {
        val item = petsList[position]

        // carrega imagem do Firebase/URL
        Glide.with(holder.imgPets.context)
            .load(item.imagemUrl)
            .into(holder.imgPets)

        holder.nome.text = item.nome
        holder.descricao.text = item.descricao
    }

    fun updateData(newItems: List<Pet>) {
        petsList.clear()
        petsList.addAll(newItems)
        notifyDataSetChanged()
    }

    inner class PetsViewHolder(binding: PetsItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val imgPets = binding.imgPets
        val nome = binding.txtNome
        val descricao = binding.txtDescricao
    }
}
