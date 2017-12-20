package com.zackyzhang.petadoptable.ui.nearby

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.zackyzhang.petadoptable.ui.R
import com.zackyzhang.petadoptable.ui.model.PetViewModel
import javax.inject.Inject

/**
 * Created by lei on 12/20/17.
 */
class AnimalAdapter @Inject constructor() : RecyclerView.Adapter<AnimalAdapter.ViewHolder>() {

    var pets: List<PetViewModel> = arrayListOf()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pet = pets[position]
        holder.nameText.text = pet.name
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

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var nameText: TextView

        init {
            nameText = view.findViewById(R.id.pet_name)
        }
    }
}