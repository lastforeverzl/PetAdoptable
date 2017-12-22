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
import com.zackyzhang.petadoptable.ui.widget.Utils
import kotlinx.android.synthetic.main.item_pet.view.*
import javax.inject.Inject

/**
 * Created by lei on 12/20/17.
 */
class AnimalAdapter @Inject constructor() : RecyclerView.Adapter<AnimalAdapter.ViewHolder>() {

    private var pets = mutableListOf<PetViewModel>()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pet = pets[position]
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

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val animalPhoto: ImageView = view.animalPhoto
        private val animalName: TextView = view.animalName
        private val animalLocation: TextView = view.animalLocation
        private val animalInfo: TextView = view.animalInfo

        fun bind(pet: PetViewModel) {
            animalName.text = pet.name
            animalInfo.text = Utils.getPetInfo(pet)
            animalLocation.text = pet.cityState

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
        }
    }
}