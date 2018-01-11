package com.zackyzhang.petadoptable.ui.search

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.annotation.ArrayRes
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.zackyzhang.petadoptable.ui.R
import com.zackyzhang.petadoptable.ui.widget.filter.FilterListener
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_search.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast



class SearchActivity : AppCompatActivity(), AnkoLogger {

    companion object {
        val ZIP_CODE = "SearchActivity:zipCode"
        val ANIMAL_FILTER = "SearchActivity:animalFilter"
        val BREED_FILTER = "SearchActivity:breedFilter"
        val SEX_FILTER = "SearchActivity:sexFilter"
        val AGE_FILTER = "SearchActivity:ageFilter"
        val SIZE_FILTER = "SearchActivity:sizeFilter"

        fun newInstance(context: Context, zipCode: String): Intent {
            val intent = Intent(context, SearchActivity::class.java)
            intent.putExtra(ZIP_CODE, zipCode)
            return intent
        }
    }

    private lateinit var zipCode: String
    private var animal = ""
    private var breed = ""
    private var sex = ""
    private var age = ""
    private var size = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        zipCode = intent.getStringExtra(ZIP_CODE)

        searchBack.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                finishAfterTransition()
            } else {
                finish()
            }
        }
        reset.setOnClickListener {
            resetFilter()
        }
        setupViewListeners()
    }

    private fun setupViewListeners() {
        animalFilter.filterListener = animalListener
        breedFilter.filterListener = breedListener
        sexFilter.filterListener = sexListener
        ageFilter.filterListener = ageListener
        sizeFilter.filterListener = sizeListener
    }

    private val animalListener = object : FilterListener {
        override fun onFilterClick() {
            createDialog(ANIMAL_FILTER, R.array.animal_filter, getString(R.string.search_animal_title))
        }
    }

    private val breedListener = object : FilterListener {
        override fun onFilterClick() {
            when (animal) {
                "dog" -> createDialog(BREED_FILTER, R.array.dog, getString(R.string.search_breed_title))
                "cat" -> createDialog(BREED_FILTER, R.array.cat, getString(R.string.search_breed_title))
                "bird" -> createDialog(BREED_FILTER, R.array.bird, getString(R.string.search_breed_title))
                "horse" -> createDialog(BREED_FILTER, R.array.horse, getString(R.string.search_breed_title))
                "reptile" -> createDialog(BREED_FILTER, R.array.reptile, getString(R.string.search_breed_title))
                "barnyard" -> createDialog(BREED_FILTER, R.array.barnyard, getString(R.string.search_breed_title))
                "smallfurry" -> createDialog(BREED_FILTER, R.array.smallfurry, getString(R.string.search_breed_title))
                else -> toast(R.string.select_animal_filter_first)
            }
        }
    }

    private val sexListener = object : FilterListener {
        override fun onFilterClick() {
            createDialog(SEX_FILTER, R.array.sex_filter, getString(R.string.search_sex_title))
        }
    }

    private val ageListener = object : FilterListener {
        override fun onFilterClick() {
            createDialog(AGE_FILTER, R.array.age_filter, getString(R.string.search_age_title))
        }
    }

    private val sizeListener = object : FilterListener {
        override fun onFilterClick() {
            createDialog(SIZE_FILTER, R.array.size_filter, getString(R.string.search_size_title))
        }
    }

    fun createDialog(filter: String, @ArrayRes resId: Int, title: String) {
        val dialogBuilder = AlertDialog.Builder(this)
        val array = resources.getStringArray(resId)
        dialogBuilder.setTitle(title)
                .setItems(resId) { _: DialogInterface, i: Int ->
                    when (filter) {
                        ANIMAL_FILTER -> {
                            animalFilter.setSelectionText(array[i])
                            animal = array[i]
                            breedFilter.setSelectionText("Any")
                            breed = ""
                        }
                        BREED_FILTER -> {
                            breedFilter.setSelectionText(array[i])
                            breed = array[i]
                        }
                        SEX_FILTER -> {
                            sexFilter.setSelectionText(array[i])
                            sex = array[i]
                        }
                        AGE_FILTER -> {
                            ageFilter.setSelectionText(array[i])
                            age = array[i]
                        }
                        SIZE_FILTER -> {
                            sizeFilter.setSelectionText(array[i])
                            size = array[i]
                        }
                    }
                }
        dialogBuilder.create().show()
    }

    fun clickApplySearch(view: View) {
        info("$animal,$breed,$sex,$age,$size")
        val intent = SearchResultActivity.newInstance(this,zipCode, encodeFilter(animal),
                encodeFilter(breed), encodeFilter(sex), encodeFilter(size), encodeFilter(age))
        startActivity(intent)
    }

    private fun resetFilter() {
        animal = ""
        breed = ""
        sex = ""
        age = ""
        size = ""
        animalFilter.setSelectionText("Any")
        breedFilter.setSelectionText("Any")
        sexFilter.setSelectionText("Any")
        ageFilter.setSelectionText("Any")
        sizeFilter.setSelectionText("Any")
    }

    private fun encodeFilter(filter: String): String {
        return when (filter) {
            "Any" -> ""
            "Male" -> "M"
            "Female" -> "F"
            "Small" -> "S"
            "Medium" -> "M"
            "Large" -> "L"
            "Extra-Large" -> "XL"
            else -> filter
        }
    }
}