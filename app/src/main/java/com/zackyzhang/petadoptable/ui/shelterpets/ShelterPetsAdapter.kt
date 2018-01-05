package com.zackyzhang.petadoptable.ui.shelterpets

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.zackyzhang.petadoptable.ui.R
import com.zackyzhang.petadoptable.ui.model.PetViewModel
import kotlinx.android.synthetic.main.item_pet.view.*
import kotlinx.android.synthetic.main.shelter_pets_header.view.*
import org.jetbrains.anko.AnkoLogger
import javax.inject.Inject

/**
 * Created by lei on 1/4/18.
 */
class ShelterPetsAdapter @Inject constructor(private val activity: ShelterPetsActivity) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>(), AnkoLogger {

    val TYPE_HEADER = 0
    val TYPE_NORMAL = 1
    private lateinit var phone: String
    private lateinit var email: String
    private lateinit var lat: String
    private lateinit var lng: String
    private lateinit var address: String

    private var pets = mutableListOf<PetViewModel>()

    lateinit var petOnClickListener: (PetViewModel) -> Unit
    lateinit var shelterOnCallListener: () -> Unit
    lateinit var shelterOnEmailListener: () -> Unit
    lateinit var shelterOnDirectionListener: () -> Unit

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        return when (getItemViewType(position)) {
            TYPE_HEADER -> (holder as HeaderHolder).bind()
            else -> (holder as ItemHolder).bind(pets[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_HEADER -> {
                val v = LayoutInflater
                        .from(parent.context)
                        .inflate(R.layout.shelter_pets_header, parent, false)
                HeaderHolder(v)
            }
            else -> {
                val v = LayoutInflater
                        .from(parent.context)
                        .inflate(R.layout.item_pet, parent, false)
                ItemHolder(v)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> TYPE_HEADER
            else -> TYPE_NORMAL
        }
    }

    override fun getItemCount(): Int {
        return pets.size
    }

    fun addPets(data: List<PetViewModel>) {
        pets.addAll(data)
    }

    fun setHeader(phone: String, email: String, lat: String, lng: String, address: String) {
        this.phone = phone
        this.email = email
        this.lat = lat
        this.lng = lng
        this.address = address
    }

    inner class HeaderHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val shelterCall: LinearLayout = view.shelterCall
        private val shelterCallImage: ImageView = view.imShelterCall
        private val shelterCallText: TextView = view.tvShelterCall
        private val shelterEmail: LinearLayout = view.shelterEmail
        private val shelterEmailImage: ImageView = view.imShelterEmail
        private val shelterEmailText: TextView = view.tvShelterEmail
        private val shelterDirection: LinearLayout = view.shelterDirection

        fun bind() {
            if (phone.isBlank()) {
                shelterCallImage.setColorFilter(ContextCompat.getColor(activity, R.color.colorGrayInactive))
                shelterCallText.setTextColor(ContextCompat.getColor(activity, R.color.colorGrayInactive))
            } else {
                shelterCall.setOnClickListener { shelterOnCallListener() }
            }
            if (email.isBlank()) {
                shelterEmailImage.setColorFilter(ContextCompat.getColor(activity, R.color.colorGrayInactive))
                shelterEmailText.setTextColor(ContextCompat.getColor(activity, R.color.colorGrayInactive))
            } else {
                shelterEmail.setOnClickListener { shelterOnEmailListener() }
            }
            shelterDirection.setOnClickListener { shelterOnDirectionListener() }
        }
    }

    inner class ItemHolder(view: View) : RecyclerView.ViewHolder(view) {
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
                itemView.setOnClickListener{ petOnClickListener(this) }
            }
        }
    }
}