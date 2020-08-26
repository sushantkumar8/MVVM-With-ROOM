package com.assignment.fluper.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.assignment.fluper.R
import com.assignment.fluper.adapter.ColorsAdapter
import com.assignment.fluper.data.repository.ProductRepository
import com.assignment.fluper.databinding.ActivityCreateProductBinding
import com.assignment.fluper.db.ProductDatabase
import com.assignment.fluper.db.ProductEntity
import com.assignment.fluper.interfaces.IColorUpdate
import com.assignment.fluper.interfaces.IOnCheckForError
import com.assignment.fluper.model.ColorModel
import com.assignment.fluper.viewmodel.ProductViewModel
import com.assignment.fluper.viewmodel.ProductViewModelFactory
import java.util.*

/**
 * @CreateProductActivity is intended for creating and updating the products
 *
 *
 * @author  Sushant Kumar
 */
class CreateProductActivity : AppCompatActivity() , IColorUpdate {
    private var mColorList: ArrayList<ColorModel>? = null
    private var colorsAdapter: ColorsAdapter? = null
    private var id = 0
    private var color : String ?= ""
    private lateinit var bindingCreateProductBinding: ActivityCreateProductBinding
    private lateinit var productViewModel: ProductViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        setupToolbar()
        initDB()
    }

    /**
     * initViews is intended for initializing the views
     */
    private fun initViews() {
        bindingCreateProductBinding = DataBindingUtil.setContentView(this,
            R.layout.activity_create_product)
        initColors()

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
     *  Initializing the DB.
     */
    private fun initDB() {
        val dao = ProductDatabase.getInstance(application).productDAO
        val repository = ProductRepository(dao)
        val factory = ProductViewModelFactory(repository)
        productViewModel = ViewModelProvider(this,factory).get(ProductViewModel::class.java)
        bindingCreateProductBinding.myViewModel = productViewModel
        productViewModel.initEditTexts(
            bindingCreateProductBinding.edtName,
            bindingCreateProductBinding.edtDescription,
            bindingCreateProductBinding.edtRegularPrice,
            bindingCreateProductBinding.edtSalePrice,
            bindingCreateProductBinding.edtProductImage,
            listner = object : IOnCheckForError {
                override fun invalidInput(message: Int) {
                    Toast.makeText(
                        this@CreateProductActivity,
                        getString(message),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            })
        bindingCreateProductBinding.lifecycleOwner = this
        productViewModel.message.observe(this, androidx.lifecycle.Observer {
            it.getContentIfNotHandled()?.let {

                Toast.makeText(
                    this,
                    it,
                    Toast.LENGTH_SHORT
                ).show()
                if(it == "Product update successfully."){
                    val resultIntent = Intent()
                    setResult(RESULT_OK, resultIntent)
                    finish()
                }
            }
        })

        data
    }

    /**
     * initColors is intended for initializing the product colors
     */
    private fun initColors() {
        mColorList = ArrayList()
        mColorList!!.add(ColorModel("#FE9647", false))
        mColorList!!.add(ColorModel("#D01408", false))
        mColorList!!.add(ColorModel("#b79268", false))
        mColorList!!.add(ColorModel("#808000", false))
        mColorList!!.add(ColorModel("#00FF00", false))
        mColorList!!.add(ColorModel("#0000FF", false))
        mColorList!!.add(ColorModel("#008000", false))
        mColorList!!.add(ColorModel("#00FFFF", false))
        mColorList!!.add(ColorModel("#008080", false))
        mColorList!!.add(ColorModel("#000080", false))
        mColorList!!.add(ColorModel("#FF00FF", false))
        mColorList!!.add(ColorModel("#800080", false))
        bindingCreateProductBinding.rvColors.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        colorsAdapter = ColorsAdapter(mColorList,this)
        bindingCreateProductBinding.rvColors.adapter = colorsAdapter
    }

    /**
     * Data is intended for getting the data from intent
     */
    private val data: Unit
         get() {
            id = intent.getIntExtra("id", 0)
            if (id > 0) {
                val name = intent.getStringExtra("name")
                val description = intent.getStringExtra("description")
                val regularPrice = intent.getStringExtra("regular_price")
                val salePrice = intent.getStringExtra("sale_price")
                color = intent.getStringExtra("color")
                val image = intent.getStringExtra("image")
                productViewModel.initUpdate(ProductEntity(id,name!!,description!!,regularPrice!! ,
                    salePrice!!,image!!,color!! ))
                bindingCreateProductBinding.edtName.setText(name)
                bindingCreateProductBinding.edtDescription.setText(description)
                bindingCreateProductBinding.edtSalePrice.setText(salePrice)
                bindingCreateProductBinding.edtRegularPrice.setText(regularPrice)
                bindingCreateProductBinding.edtProductImage.setText(image)
                bindingCreateProductBinding.btnAddProduct.text = getString(R.string
                    .txt_update_product)
                title = getString(R.string.txt_update_product)
                setSelectedColor(color!!)
            }
        }

    /**
     * setSelectedColor is intended for set selected color
     *
     * @param color
     *
     *The selected color code
     */
    private fun setSelectedColor(color: String) {
        productViewModel.colorCode = color
        for (e in mColorList!!.indices) {
            if (mColorList!![e].colorCode.equals(
                    color,
                    ignoreCase = true
                )
            ) mColorList!![e].isSelected = true

        }
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


    override fun updateColorCode() {
        for (e in mColorList!!.indices) {
            if (mColorList!![e].isSelected){
                color = mColorList!![e].colorCode
                productViewModel.colorCode = color
            }
        }
    }
}