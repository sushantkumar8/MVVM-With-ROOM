package com.assignment.fluper.viewmodel

import android.widget.EditText
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.*
import com.assignment.fluper.R
import com.assignment.fluper.data.repository.ProductRepository
import com.assignment.fluper.db.Event
import com.assignment.fluper.db.ProductEntity
import com.assignment.fluper.interfaces.IOnCheckForError
import com.assignment.fluper.utils.AppLogger
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * @ProductViewModel is intended for View Model Class
 *
 *
 * @author  Sushant Kumar
 */
class ProductViewModel(private val repository : ProductRepository) : ViewModel(),Observable {

   fun getAllProduct(): LiveData<List<ProductEntity>> {
        return repository.getAllProduct()
    }
    var id : Int ? = 0
    var colorCode : String ? = ""

    fun getsingleProduct(id:String): LiveData<ProductEntity> {
        return repository.getProduct(id)
    }
    private var isUpdate = false
    private lateinit var productUpdateOrDelete : ProductEntity

    @Bindable
    val productName = MutableLiveData<String>()

    @Bindable
    val productDescription = MutableLiveData<String>()

    @Bindable
    val productRegularPrice = MutableLiveData<String>()

    @Bindable
    val productSalePrice = MutableLiveData<String>()

    @Bindable
    val productPhoto = MutableLiveData<String>()

    @Bindable
    val productBtnUpdate = MutableLiveData<String>()

    init {
        productBtnUpdate.value = "ADD PRODUCT"
    }

    private val statusMessage = MutableLiveData<Event<String>>()

    val message:LiveData<Event<String>>
    get()  = statusMessage

    var listEditText : MutableList<EditText> = mutableListOf()
    var listner: IOnCheckForError?=null



    fun addProduct(){


    var isChecked = true
        for(editText in listEditText){
            if(checkForError(editText)){
                isChecked = false
                break
            }
        }
        if(!isChecked)
            return
        if(colorCode?.isEmpty()!!){
            statusMessage.value = Event("Please select the color code")
            return
        }
        val name = productName.value!!
        val description = productDescription.value!!
        val regularPrice = productRegularPrice.value!!
        val salePrice = productSalePrice.value!!
        val photo = productPhoto.value!!
        if(isUpdate){
            update(ProductEntity(id!!,name,description,regularPrice ,salePrice,photo,colorCode!! ))
        }else{
            insert(ProductEntity(0,name,description,regularPrice ,salePrice,photo,colorCode!! ))
        }
        productName.value = null
        productDescription.value = null
        productRegularPrice.value = null
        productSalePrice.value = null
        productPhoto.value = null
    }

    fun initEditTexts(vararg editText: EditText, listner: IOnCheckForError){
        for (view in editText)
        listEditText.add(view)
        this.listner=listner
    }

    fun initUpdate(productEntity: ProductEntity){
        id = productEntity.id
        productName.value = productEntity.name
        productDescription.value = productEntity.description
        productRegularPrice.value = productEntity.regular_price
        productSalePrice.value = productEntity.sale_price
        productPhoto.value = productEntity.product_photo
        isUpdate = true
        productUpdateOrDelete = productEntity
        productBtnUpdate.value = "UPDATE PRODUCT"

    }

    fun initDelete(productEntity: ProductEntity){
        id = productEntity.id
        productName.value = productEntity.name
        productDescription.value = productEntity.description
        productRegularPrice.value = productEntity.regular_price
        productSalePrice.value = productEntity.sale_price
        productPhoto.value = productEntity.product_photo
        productUpdateOrDelete = productEntity
        delete(productUpdateOrDelete)
    }

    private fun checkForError(editText: EditText):Boolean{
       var isError = false;
        when (editText.id){
            R.id.edt_name -> {
                            if (editText.text.toString().isEmpty()) {
                                listner?.invalidInput(R.string.txt_please_enter_product_name)
                                isError=true;
            }
            }
            R.id.edt_description -> {
                if (editText.text.toString().isEmpty()) {
                    listner?.invalidInput(R.string.txt_please_enter_product_description)
                    isError=true;
                }
            }
            R.id.edt_regular_price -> {
                if (editText.text.toString().isEmpty()) {
                    listner?.invalidInput(R.string.txt_please_enter_regular_price)
                    isError=true;
                }
            }
            R.id.edt_sale_price -> {
                if (editText.text.toString().isEmpty()) {
                    listner?.invalidInput(R.string.txt_please_enter_sale_price)
                    isError=true;
                }
            }
            R.id.edt_product_image -> {
                if (editText.text.toString().isEmpty()) {
                    listner?.invalidInput(R.string.txt_please_enter_product_image)
                    isError=true;
                }
            }
        }
        return isError
    }

    fun insert(product : ProductEntity):Job = viewModelScope.launch {
            repository.insert(product)
            AppLogger.e("The Products are ", " $product");
            statusMessage.value = Event("Product insert successfully.")
    }

    fun update(product : ProductEntity):Job = viewModelScope.launch {
        repository.update(product)
        statusMessage.value = Event("Product update successfully.")
    }

    fun delete(product : ProductEntity):Job = viewModelScope.launch {
        repository.delete(product)
        statusMessage.value = Event("Product delete successfully.")
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }
    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }


}