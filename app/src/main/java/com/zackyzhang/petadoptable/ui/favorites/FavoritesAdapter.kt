package com.zackyzhang.petadoptable.ui.favorites

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.zackyzhang.petadoptable.ui.model.PetViewModel
import org.jetbrains.anko.AnkoLogger

/**
 * Using AnimalAdapter for now.
 */
class FavoritesAdapter constructor() : RecyclerView.Adapter<FavoritesAdapter.ViewHolder>(),
        AnkoLogger{

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(pet: PetViewModel) {

        }
    }
}