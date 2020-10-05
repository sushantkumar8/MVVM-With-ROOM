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
        // Initializing the layout should be in onCreate() method, and the views initialization should be in onStart()
        bindingMainActivity = ActivityMainBinding.inflate(layoutInflater)
        val view: View = bindingMainActivity.root
        setContentView(view)
    }

    // Ideally we should be initializing the views on onStart() instead of onCreate()
    override fun onStart() {
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
    
    /**
     * Overriding the default methods of Activity
     */
    override fun onPause() {
    // call the superclass method first
        super.onStop()
    }    
    
    override fun onStop() {
    // call the superclass method first
        super.onStop()
    } 
}
