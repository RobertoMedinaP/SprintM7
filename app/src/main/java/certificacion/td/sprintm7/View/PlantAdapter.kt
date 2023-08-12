package certificacion.td.sprintm7.View

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import certificacion.td.sprintm7.Model.Local.Entities.PlantEntity
import certificacion.td.sprintm7.databinding.ItemListBinding
import com.bumptech.glide.Glide


class PlantAdapter : RecyclerView.Adapter<PlantAdapter.PlantViewHolder>() {


    private var plantList = listOf<PlantEntity>()
    private val selectedPlant = MutableLiveData<PlantEntity>()

    inner class PlantViewHolder(private val binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {
        fun bind(plantEntity: PlantEntity) {
            Glide.with(binding.imageView).load(plantEntity.imagen).into(binding.imageView)
            binding.tv1.text = plantEntity.id.toString()
            binding.tv2.text = plantEntity.nombre
            binding.tv3.text = plantEntity.tipo
            binding.tv4.text = plantEntity.descripcion
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            selectedPlant.value = plantList[adapterPosition]
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantViewHolder {
        val binding = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlantViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return plantList.size
    }

    override fun onBindViewHolder(holder: PlantViewHolder, position: Int) {
        val selectedPlant = plantList[position]
        holder.bind(selectedPlant)
    }

    fun elementoSeleccionado(): LiveData<PlantEntity> = selectedPlant

    fun updateData(list: List<PlantEntity>) {
        plantList = list
        notifyDataSetChanged()
    }


}