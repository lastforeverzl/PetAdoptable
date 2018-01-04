package com.zackyzhang.petadoptable.ui.nearby

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.zackyzhang.petadoptable.ui.R
import com.zackyzhang.petadoptable.ui.model.PetViewModel
import kotlinx.android.synthetic.main.item_pet.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import javax.inject.Inject

/**
 * Created by lei on 12/20/17.
 */
class AnimalAdapter @Inject constructor() :
        RecyclerView.Adapter<AnimalAdapter.ViewHolder>(), AnkoLogger {

    private var pets = mutableListOf<PetViewModel>()

    lateinit var listener: (PetViewModel) -> Unit

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pet = pets[position]
        info("${ pet.name } is favorite: ${ pet.isFavorite }")
        holder.bind(pet)
    }

    override fun getItemCount(): Int {
        return pets.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_pet, parent, false)
        return ViewHolder(itemView)
    }

    fun addPets(data: List<PetViewModel>) {
        pets.addAll(data)
    }

    fun clearAdapter() {
        pets.clear()
    }

    inner class ViewHolder(view: View) :
            RecyclerView.ViewHolder(view) {

        private val animalPhoto: ImageView = view.animalPhoto
        private val animalName: TextView = view.animalName
        private val animalLocation: TextView = view.animalLocation
        private val animalInfo: TextView = view.animalInfo

        fun bind(pet: PetViewModel) {
            with(pet) {
                animalName.text = pet.name
                animalInfo.text = this.getPetInfo()
                animalLocation.text = pet.cityState

                //todo("try using kotlin extension function for Glide")
                if (pet.medias.isNotEmpty()) {
                    Glide.with(itemView.context)
                            .asBitmap()
                            .load(pet.medias[0])
                            .thumbnail(0.2f)
                            .into(animalPhoto)
                } else {
                    Glide.with(itemView.context)
                            .asBitmap()
                            .load(R.drawable.no_image_placeholder)
                            .thumbnail(0.2f)
                            .into(animalPhoto)
                }
                itemView.setOnClickListener{ listener(this) }
            }
        }
    }
}