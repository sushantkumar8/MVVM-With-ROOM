package com.assignment.fluper.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.assignment.fluper.adapter.ProductJSONAdapter
import com.assignment.fluper.adapter.ProductDBAdapter
import com.assignment.fluper.data.repository.ProductRepository
import com.assignment.fluper.databinding.ActivityShowProductBinding
import com.assignment.fluper.db.ProductDatabase
import com.assignment.fluper.db.ProductEntity
import com.assignment.fluper.interfaces.IProductActions
import com.assignment.fluper.model.Product
import com.assignment.fluper.utils.Constant
import com.assignment.fluper.utils.Singleton
import com.assignment.fluper.utils.Util
import com.assignment.fluper.viewmodel.ProductViewModel
import com.assignment.fluper.viewmodel.ProductViewModelFactory
import org.json.JSONArray
import java.util.*

/**
 *  @ShowProductActivity,
 *  This class is responsible for showing the List of Products.
 *  At first, there will 3 products which is hardcoded.
 *  Product can be clicked, and can be directed to @ProductDetail.
 *
 *  @author  Sushant Kumar
 */
class ShowProductActivity : AppCompatActivity() {
    private var productJSONAdapter: ProductJSONAdapter? = null
    private var productList: MutableList<Product>? = null
    private lateinit var bindingShowProductBinding: ActivityShowProductBinding
    private lateinit var productViewModel: ProductViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        setupToolbar()
        initDB()
        initRecyclerViews()

        Singleton.IProductListener = object: IProductActions {
            override fun updateData() {
                initRecyclerViews()
            }
        }
    }

    /**
     *  Initializing the View.
     */
    private fun initView() {
        bindingShowProductBinding = ActivityShowProductBinding.inflate(layoutInflater)
        val view: View = bindingShowProductBinding.root
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
        bindingShowProductBinding.myViewModel = productViewModel
        bindingShowProductBinding.lifecycleOwner = this
    }

    /**
     * Initializing Recycler View.
     */
    private fun initRecyclerViews(){
        bindingShowProductBinding.rvProducts.layoutManager = LinearLayoutManager(this)
        displaysProductList()
    }

    /**
     *  Displaying the Product List which is stored in the DB.
     */
    private fun displaysProductList() {
        productViewModel.getAllProduct().observe(this, {
            if(it.size>0){
                bindingShowProductBinding.rvProducts.adapter = ProductDBAdapter(
                    this,it,{selectedItem:ProductEntity->listItemClicked(selectedItem)},
                    {selectedItem:ProductEntity->listItemImageClicked(selectedItem)})
            }else{
                createProductsFromJson()
            }
        })
    }

    /**
     * Initializing the Item Click.
     */
    private fun listItemClicked(productEntity: ProductEntity){
            val intent = Intent(this, ProductDetailsActivity::class.java)
            intent.putExtra(Constant.ID, productEntity.id)
            intent.putExtra(Constant.JSON, Constant.NO)
            startActivity(intent)
    }

    /**
     * This will be invoked when Image of the item is clicked.
     */
    private fun listItemImageClicked(productEntity: ProductEntity){
        val  intent = Intent(this, ImageActivity::class.java)
             intent.putExtra(Constant.IMAGE, productEntity.product_photo)
             intent.putExtra(Constant.TITLE, productEntity.name)
             startActivity(intent)
    }


    /**
     *  This will be create Product mock data for the models using json.
     */
    private fun createProductsFromJson() {
        productList = ArrayList()
        try {
            val productJsonArray = JSONArray(Util.readJSONFromAsset(this))
            for (i in 0 until productJsonArray.length()) {
                val productJsonObject = productJsonArray.getJSONObject(i)
                val name = productJsonObject.optString(Constant.NAME)
                val description = productJsonObject.optString(Constant.DESCRIPTION)
                val color = productJsonObject.optString(Constant.COLOR)
                val regularPrice = productJsonObject.optDouble(Constant.REGULAR_PRICE)
                val salePrice = productJsonObject.optDouble(Constant.SALE_PRICE)
                val productPhoto = productJsonObject.optString(Constant.PRODUCT_PHOTO)
                val product =
                    Product(1, name, description, regularPrice, salePrice, productPhoto, color)
                productList?.add(product)
            }

            bindingShowProductBinding.rvProducts.setLayoutManager(LinearLayoutManager(this))
            productJSONAdapter = ProductJSONAdapter(this, productList)
            bindingShowProductBinding.rvProducts.adapter = productJSONAdapter
            productJSONAdapter?.notifyDataSetChanged()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * This method is showing the ack Button on the toolbar, which is invoking the onBackPress()
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}