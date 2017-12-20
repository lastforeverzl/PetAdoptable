package com.zackyzhang.petadoptable.ui.favorites

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zackyzhang.petadoptable.ui.R
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_favorites.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

/**
 * Created by lei on 12/18/17.
 */
class FavoritesFragment : Fragment(), AnkoLogger {

    companion object {
        fun newInstance(): FavoritesFragment {
            return FavoritesFragment()
        }
    }

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        noPetsFound.visibility = View.VISIBLE
        noPetsFound.text = "FavoritesFragment"
    }

    override fun onDestroy() {
        info("onDestroy")
        super.onDestroy()
    }
}