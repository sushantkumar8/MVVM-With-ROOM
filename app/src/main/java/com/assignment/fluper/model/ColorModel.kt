package com.assignment.fluper.model

/**
 *  @ColorModel: It is the Model Class,
 *
 *  @param colorCode
 *  @param isSelected
 *
 *  @author  Sushant Kumar
 */
class ColorModel(colorCode: String, isSelected: Boolean) {
    var colorCode = ""
    var isSelected = false

    init {
        this.colorCode = colorCode
        this.isSelected = isSelected
    }
}