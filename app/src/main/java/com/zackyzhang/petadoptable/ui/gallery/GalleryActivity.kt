package com.zackyzhang.petadoptable.ui.gallery

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.bumptech.glide.Glide
import com.github.chrisbanes.photoview.PhotoView
import com.zackyzhang.petadoptable.ui.R
import kotlinx.android.synthetic.main.activity_gallery.*
import org.jetbrains.anko.AnkoLogger

class GalleryActivity : AppCompatActivity(), AnkoLogger {

    lateinit var urls: List<String>
    private var position = 0

    companion object {

        val IMAGES_URLS = "GalleryActivity.gallery_urls"
        val IMAGE_POSITION = "GalleryActivity.gallery_position"

        fun newInstance(context: Context, urls: ArrayList<String>, position: Int): Intent {
            val intent = Intent(context, GalleryActivity::class.java)
            intent.putStringArrayListExtra(IMAGES_URLS, urls)
            intent.putExtra(IMAGE_POSITION, position)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        urls = intent.getStringArrayListExtra(IMAGES_URLS)
        position = intent.getIntExtra(IMAGE_POSITION, 0)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setToolbarTitle(position)
        setupViewPager()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item!!.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setToolbarTitle(position: Int) {
        supportActionBar!!.title = "${ position + 1 }/${urls.size}"
    }

    private fun setupViewPager() {
        viewPager.adapter = SamplePagerAdapter(this)
        viewPager.currentItem = position
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                setToolbarTitle(position)
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
    }

    inner class SamplePagerAdapter constructor(val context: Context): PagerAdapter() {

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val photoView = PhotoView(container.context)
            Glide.with(context)
                    .asBitmap()
                    .load(urls[position])
                    .into(photoView)
            container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT)
            return photoView
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view == `object`
        }

        override fun getCount(): Int {
            return urls.size
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View)
        }
    }
}