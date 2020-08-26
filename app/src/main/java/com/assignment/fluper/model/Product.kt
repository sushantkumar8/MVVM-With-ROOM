package com.assignment.fluper.model

/**
 *  @Product: It is the Model Class,
 *
 *  @param id
 *  @param name
 *  @param description
 *  @param regular_price
 *  @param sale_price
 *  @param product_photo
 *  @param selected_color
 *
 *  @author  Sushant Kumar
 */
class Product(
    id: Int,
    name: String,
    description: String,
    regular_price: Double,
    sale_price: Double,
    product_photo: String,
    selected_color: String
) {
    var id = 0
    var name = ""
    var description = ""
    var regularPrice = 0.0
    var salePrice = 0.0
    var productPhoto = ""
    var selectedColor = ""

    init {
        this.id = id
        this.name = name
        this.description = description
        regularPrice = regular_price
        salePrice = sale_price
        productPhoto = product_photo
        selectedColor = selected_color
    }
}