package com.assignment.fluper.utils

import android.content.Context
import java.io.InputStream
import java.math.BigDecimal
import java.text.DecimalFormat

/**
 *  @Util: It is the Utilities Class,
 *
 *  @see changeAmountWithComma
 *
 *  @author  Sushant Kumar
 */
object Util {

    /**
     * @changeAmountWithComma Change amount with comma
     */
    fun changeAmountWithComma(amount: Double): String {
        val bd = BigDecimal(amount)
        val df = DecimalFormat()
        df.maximumFractionDigits = 2 //Sets the maximum number of digits after the decimal point
        df.minimumFractionDigits = 0 //Sets the minimum number of digits after the decimal point
        df.isGroupingUsed = true
        return df.format(bd)
    }


    /**
     * @readJSONFromAsset Read json data from assets Folder
     */
    fun readJSONFromAsset(context:Context): String? {
        var json: String? = null
        try {
            val  inputStream:InputStream = context.assets.open("product_mock.json")
            json = inputStream.bufferedReader().use{it.readText()}
        } catch (ex: Exception) {
            ex.printStackTrace()
            return null
        }
        return json
    }

}