package com.assignment.fluper.data.repository

import androidx.lifecycle.LiveData
import com.assignment.fluper.db.ProductDAO
import com.assignment.fluper.db.ProductEntity


/**
 *  @ProductRepository: It is the Repository get data from DAO
 *
 *  @see getAllProduct
 *  @see insert
 *  @see update
 *  @see delete
 *  @see getProduct
 *
 *  @author  Sushant Kumar
 */
class ProductRepository(private val dao : ProductDAO) {

    fun getAllProduct(): LiveData<List<ProductEntity>> {
        return dao.getAllProducts()
    }

    suspend fun insert(product : ProductEntity){
        dao.insertProduct(product)
    }

    suspend fun update(product : ProductEntity){
        dao.updateProduct(product)
    }

    suspend fun delete(product : ProductEntity){
        dao.deleteProduct(product)
    }

    fun getProduct(id:String): LiveData<ProductEntity> {
        return dao.getSingleProducts(id)

    }


}