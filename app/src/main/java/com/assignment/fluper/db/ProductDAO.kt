package com.assignment.fluper.db

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 *  @ProductDAO: It is the Dao,
 *                which holds the all the Entity.
 *  @see  insertProduct
 *  @see  updateProduct
 *  @see  deleteProduct
 *  @see  getAllProducts
 *  @see  getSingleProducts
 *
 *  @author  Sushant Kumar
 */
@Dao
interface ProductDAO {

    @Insert
    suspend fun insertProduct(productEntity: ProductEntity)

    @Update
    suspend fun updateProduct(productEntity: ProductEntity)

    @Delete
    suspend fun deleteProduct(productEntity: ProductEntity)

    @Query("SELECT  * FROM PRODUCT")
    fun getAllProducts():LiveData<List<ProductEntity>>

    @Query("SELECT  * FROM PRODUCT WHERE PRODUCT_ID = :id")
    fun getSingleProducts(id : String):LiveData<ProductEntity>
}