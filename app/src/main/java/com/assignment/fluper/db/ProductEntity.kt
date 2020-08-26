package com.assignment.fluper.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


/**
 *  @ProductEntity: It is the Entity of the Room Database ,
 *                which holds the all @PrimaryKey @ColumnInfo.
 *  @see PrimaryKey
 *  @see ColumnInfo
 *
 *  @author  Sushant Kumar
 */
@Entity(tableName = "PRODUCT")
data class ProductEntity (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "PRODUCT_ID")
    val id: Int,

    @ColumnInfo(name = "PRODUCT_NAME")
    val name: String,

    @ColumnInfo(name = "PRODUCT_DESCRIPTION")
    val description: String,

    @ColumnInfo(name = "REGULAR_PRICE")
    val regular_price: String,

    @ColumnInfo(name = "SALE_PRICE")
    val sale_price: String,

    @ColumnInfo(name = "PRODUCT_PHOTO")
    val  product_photo: String,

    @ColumnInfo(name = "PRODUCT_COLOR")
    val selected_color: String
)

