package com.zackyzhang.petadoptable.ui.shelters

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.zackyzhang.petadoptable.ui.R
import com.zackyzhang.petadoptable.ui.main.MainActivity
import com.zackyzhang.petadoptable.ui.model.ShelterViewModel
import kotlinx.android.synthetic.main.item_shelter.view.*
import org.jetbrains.anko.AnkoLogger
import javax.inject.Inject

class SheltersAdapter @Inject constructor() : RecyclerView.Adapter<SheltersAdapter.ViewHolder>(),
        AnkoLogger {

    @Inject lateinit var activity: MainActivity
    private var shelters = mutableListOf<ShelterViewModel>()

    lateinit var shelterOnClickListener: (ShelterViewModel) -> Unit
    lateinit var shelterDirectionListener: (String, String, String) -> Unit
    lateinit var shelterCallListener: (String) -> Unit

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val shelter = shelters[position]
        holder.bind(shelter)
    }

    override fun getItemCount(): Int {
        return shelters.size
    }

    fun addShelters(data: List<ShelterViewModel>) {
        shelters.addAll(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_shelter, parent, false)
        return ViewHolder(itemView)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val shelterLayout: LinearLayout = view.shelterLayout
        private val shelterName: TextView = view.shelterName
        private val shelterAddress: TextView = view.shelterAddress
        private val shelterPhone: TextView = view.shelterPhone
        private val shelterCallIcon: ImageView = view.imShelterCall
        private val shelterCallText: TextView = view.tvShelterCall
        private val shelterEmail: TextView = view.shelterEmail
        private val shelterDirection: TextView = view.shelterDirection
        private val shelterCall: LinearLayout = view.shelterCall

        fun bind(shelter: ShelterViewModel) {
            shelterName.text = shelter.name
            shelterAddress.text = shelter.address
            shelterPhone.text = shelter.phone

            if (shelter.phone.isNotEmpty()) {
                shelterCallIcon.setColorFilter(ContextCompat.getColor(activity, R.color.colorPrimary))
                shelterCallText.setTextColor(ContextCompat.getColor(activity, R.color.colorPrimary))
            } else {
                shelterCallIcon.setColorFilter(ContextCompat.getColor(activity, R.color.colorGrayInactive))
                shelterCallText.setTextColor(ContextCompat.getColor(activity, R.color.colorGrayInactive))
            }
            shelterEmail.text = shelter.email
            shelterLayout.setOnClickListener { shelterOnClickListener(shelter) }
            shelterDirection.setOnClickListener {
                shelterDirectionListener(shelter.latitude, shelter.longitude, shelter.address)
            }
            shelterCall.setOnClickListener {
                shelterCallListener(shelter.phone)
            }
        }
    }
}