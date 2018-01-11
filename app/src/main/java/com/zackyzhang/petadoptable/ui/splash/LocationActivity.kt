package com.zackyzhang.petadoptable.ui.splash

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.zackyzhang.petadoptable.ui.R
import com.zackyzhang.petadoptable.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_location.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.startActivity

class LocationActivity : AppCompatActivity(), AnkoLogger {

    private lateinit var zipCode: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)
    }

    fun clickApplyButton(view: View) {
        zipCode = zipCodeEditText.text.toString()
        info(zipCode)
        startActivity<MainActivity>(MainActivity.ZIP_CODE to zipCode)
        finish()
    }
}