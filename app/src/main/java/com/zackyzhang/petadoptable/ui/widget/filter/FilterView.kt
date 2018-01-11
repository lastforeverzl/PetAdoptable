package com.zackyzhang.petadoptable.ui.widget.filter

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.zackyzhang.petadoptable.ui.R
import kotlinx.android.synthetic.main.view_filter.view.*

class FilterView : LinearLayout {

    var filterListener: FilterListener? = null

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        LayoutInflater.from(context).inflate(R.layout.view_filter, this)
        filterLayout.setOnClickListener { filterListener?.onFilterClick() }
        attrs?.let {
            val ta = context.obtainStyledAttributes(attrs, R.styleable.FilterView, 0, 0)
            filterTitle.text = ta.getString(R.styleable.FilterView_title)
            ta.recycle()
        }
    }

    fun setSelectionText(text: String) {
        filterSelection.text = text
    }

}