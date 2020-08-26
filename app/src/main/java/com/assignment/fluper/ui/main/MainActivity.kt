package com.assignment.fluper.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.assignment.fluper.databinding.ActivityMainBinding

/**
 *  @MainActivity: It is the launcher activity,
 *                 which holds two button, Show and Create.
 *
 *  @author  Sushant Kumar
 */
class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var bindingMainActivity: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
    }

    /**
     *  Initializing the View
     */
    private fun initViews() {
        bindingMainActivity = ActivityMainBinding.inflate(layoutInflater)
        val view: View = bindingMainActivity.root
        setContentView(view)
        initListeners()
    }

    /**
     *  Initializing the Click Listeners to Show Button and Create Button
     */
    private fun initListeners() {
        bindingMainActivity.btnShowProduct.setOnClickListener(this)
        bindingMainActivity.btnCreateProduct.setOnClickListener(this)
    }

    /**
     * Performing the click listener.
     */
    override fun onClick(v: View) {
        if (v === bindingMainActivity.btnShowProduct) {
            startActivity(Intent(this, ShowProductActivity::class.java))
        }
        if (v === bindingMainActivity.btnCreateProduct) {
            startActivity(Intent(this, CreateProductActivity::class.java))
        }
    }
}