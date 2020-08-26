package com.assignment.fluper.ui.main

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.assignment.fluper.R
import com.assignment.fluper.databinding.ActivityImageBinding
import com.assignment.fluper.utils.Constant
import com.bumptech.glide.Glide

/**
 * @ImageActivity is intended for show product image
 *
 *
 * @author  Sushant Kumar
 */
class ImageActivity : AppCompatActivity() {
    private lateinit var bindingImageBinding: ActivityImageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingImageBinding = ActivityImageBinding.inflate(layoutInflater)
        val view: View = bindingImageBinding.root
        setContentView(view)
        initViews()
        setupToolbar()
    }

    /**
     * setupToolbar is intended for initializing the toolbar
     */
    private fun setupToolbar() {
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowHomeEnabled(true)
        }
    }

    /**
     * initViews is intended for initializing the views
     */
    private fun initViews() {
        data
    }

    /**
     * getData is intended for getting the data from intent
     */
    private val data: Unit
         get() {
            val image = intent.getStringExtra(Constant.IMAGE)
            val title = intent.getStringExtra(Constant.TITLE)
            if (title != null && title.isNotEmpty()) setTitle(title)
            if (image != null && image.isNotEmpty()) Glide.with(this)
                .load(image)
                .thumbnail(0.1f)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(bindingImageBinding.ivProductImage)
        }

    /**
     * onOptionsItemSelected is intended to select menu items from the dashboard.
     *
     * @param item
     *
     *The Menu Item
     * @return
     *
     *Return boolean type
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}