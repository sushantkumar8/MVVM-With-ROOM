package com.assignment.fluper.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.assignment.fluper.data.repository.ProductRepository
import java.lang.IllegalArgumentException

/**
 * @ProductViewModelFactory is intended for View Model Factory Class
 *
 *
 * @author  Sushant Kumar
 */
class ProductViewModelFactory(private val respository:ProductRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ProductViewModel::class.java)){
            return ProductViewModel(respository) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }
}