package com.assignment.fluper.ui.main

import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.assignment.fluper.R
import com.assignment.fluper.data.repository.ProductRepository
import com.assignment.fluper.databinding.ActivityProductBinding
import com.assignment.fluper.db.ProductDatabase
import com.assignment.fluper.db.ProductEntity
import com.assignment.fluper.utils.Singleton
import com.assignment.fluper.viewmodel.ProductViewModel
import com.assignment.fluper.viewmodel.ProductViewModelFactory
import com.bumptech.glide.Glide

/**
 * @ProductDetailsActivity is intended for showing the details of product
 *
 *
 * @author  Sushant Kumar
 */
class ProductDetailsActivity : AppCompatActivity() {
    var id : Int = 0
    private lateinit var productViewModel: ProductViewModel
    private lateinit var productDBS: ProductEntity
    private lateinit var bindingProductBinding: ActivityProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        setupToolbar()
        initDB()
        displaysSingleProductDB()
    }


    /**
     * Initializing the View.
     */
    private fun initView() {
        bindingProductBinding = ActivityProductBinding.inflate(layoutInflater)
        val view: View = bindingProductBinding.root
        setContentView(view)

    }

    /**
     *  Initializing the Toolbar.
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
        bindingProductBinding.myViewModel = productViewModel
        bindingProductBinding.lifecycleOwner = this
        productViewModel.message.observe(this, {
            it.getContentIfNotHandled()?.let {
                if(it == "Product delete successfully."){
                    Toast.makeText(
                        this,
                        it,
                        Toast.LENGTH_SHORT
                    ).show()
                    Singleton.IProductListener?.updateData()
                    finish()
                }

            }
        })
        data

    }

    /**
     * Displaying the Product Details which is stored in the DB.
     */
    private fun displaysSingleProductDB(){
        productViewModel.getsingleProduct(id.toString()).observe(this,
            {

            if (it!=null){
                productDBS = it
                bindingProductBinding.tvProductTitle.setText(productDBS.name)
                bindingProductBinding.tvProductDescription.setText(productDBS.description)

                bindingProductBinding.tvRegularPrice.setText(
                    getString(R.string.txt_rupee_sumbol) + productDBS.regular_price.toString()
                )
                bindingProductBinding.tvSalePrice.setText(
                    getString(R.string.txt_rupee_sumbol) + productDBS.sale_price.toString()
                )
                bindingProductBinding.tvRegularPrice.paintFlags =
                    bindingProductBinding.tvRegularPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

                bindingProductBinding.tvRegularPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

                if (!productDBS.product_photo.isEmpty()) Glide.with(this)
                    .load(productDBS.product_photo)
                    .thumbnail(0.1f)
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(bindingProductBinding.ivProductImage)
                bindingProductBinding.ivColor.setColorFilter(
                    Color.parseColor(productDBS.selected_color),
                    PorterDuff.Mode.MULTIPLY
                )
                bindingProductBinding.btnUpdate.setOnClickListener {
                    updateProduct()
                }

                bindingProductBinding.btnDelete.setOnClickListener {
                    deleteProduct()
                }

                bindingProductBinding.ivProductImage.setOnClickListener {
                    val intent = Intent(this, ImageActivity::class.java)
                    intent.putExtra("image", productDBS.product_photo)
                    intent.putExtra("title", productDBS.name)
                    startActivity(intent)
                }
            }


        })

    }


    /**
     *  data is intended for getting the data from intent
     */
    private val data: Unit
         get() {
            id = intent.getIntExtra("id",0)
            val Json = intent.getStringExtra("Json")
            if(Json == "Yes"){
                val name = intent.getStringExtra("name")
                val description = intent.getStringExtra("description")
                val regularPrice = intent.getStringExtra("regularPrice")
                val salePrice = intent.getStringExtra("salePrice")
                val productPhoto = intent.getStringExtra("productPhoto")
                val selectedColor = intent.getStringExtra("selectedColor")
                setJsonData(name,description,regularPrice,salePrice,productPhoto,selectedColor)
            }else{
                displaysSingleProductDB()
            }
        }


    /**
     *  This will be set product from json.
     *  @param name
     *  @param description
     *  @param regularPrice
     *  @param salePrice
     *  @param productPhoto
     *  @param selectedColor
     */
    fun setJsonData(
        name: String?,
        description: String?,
        regularPrice: String?,
        salePrice: String?,
        productPhoto: String?,
        selectedColor: String?
    ) {
        bindingProductBinding.tvProductTitle.setText(name)
        bindingProductBinding.tvProductDescription.setText(description)

        bindingProductBinding.tvRegularPrice.setText(
            getString(R.string.txt_rupee_sumbol) + regularPrice
        )
        bindingProductBinding.tvSalePrice.setText(
            getString(R.string.txt_rupee_sumbol) + salePrice
        )


        bindingProductBinding.tvRegularPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

        if (!productPhoto?.isEmpty()!!) Glide.with(this)
            .load(productPhoto)
            .thumbnail(0.1f)
            .placeholder(R.mipmap.ic_launcher)
            .error(R.mipmap.ic_launcher)
            .into(bindingProductBinding.ivProductImage)
        bindingProductBinding.ivColor.setColorFilter(
            Color.parseColor(selectedColor),
            PorterDuff.Mode.MULTIPLY
        )
        bindingProductBinding.btnUpdate.visibility = View.GONE
        bindingProductBinding.btnDelete.visibility = View.GONE
    }


    /**
     * updateProduct is intended for updating the existing product
     *
     * The updating product position
     */
     fun updateProduct() {
            val intent = Intent(this, CreateProductActivity::class.java)
            intent.putExtra("regular_price", productDBS.regular_price)
            intent.putExtra("sale_price", productDBS.sale_price)
            intent.putExtra("name", productDBS.name)
            intent.putExtra("description", productDBS.description)
            intent.putExtra("color",productDBS.selected_color)
            intent.putExtra("id", id)
            intent.putExtra("image", productDBS.product_photo)
            startActivityForResult(intent, 100)
    }

    /**
     * deleteProduct is intended for deleting the existing product into the database
     *
     * The delete item position
     */
    fun deleteProduct() {
        productViewModel.initDelete(ProductEntity(id,productDBS.name,productDBS.description,
            productDBS.regular_price ,productDBS.sale_price,productDBS.product_photo
            ,productDBS.selected_color ))
    }

    /**
     * onActivityResult is intended to get the result after intent fire for authorization code
     *
     * @param requestCode
     *
     *requestCode is a int type value
     * @param resultCode
     *
     *resultCode is a int type value
     * @param data
     *
     *data is a intent carrying data
     */
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100) {
            displaysSingleProductDB()
            Singleton.IProductListener?.updateData()
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

}