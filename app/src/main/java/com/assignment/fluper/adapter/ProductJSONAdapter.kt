package com.assignment.fluper.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.assignment.fluper.R
import com.assignment.fluper.model.Product
import com.assignment.fluper.ui.main.ImageActivity
import com.assignment.fluper.ui.main.ProductDetailsActivity
import com.assignment.fluper.utils.Util
import com.bumptech.glide.Glide

/**
 * @ProductJSONAdapter is intended for set the list of products from JSON
 *
 * @author  Sushant Kumar
 */
class ProductJSONAdapter(
    private val context: Context, private var mProductList: List<Product>?
) : RecyclerView.Adapter<ProductJSONAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.cart_product_item_json, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView")
    position: Int) {
        val model = mProductList!![position]
        holder.mTitleView.text = model.name
        holder.mDescriptionView.text = model.description
        holder.mSalePriceView.text =
            context.getString(R.string.txt_rupee_sumbol) + Util.changeAmountWithComma(model.salePrice)
        holder.mRegularPriceView.text =
            context.getString(R.string.txt_rupee_sumbol) + Util.changeAmountWithComma(model.regularPrice)
        holder.mRegularPriceView.paintFlags =
            holder.mRegularPriceView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG


        if (!model.productPhoto.isEmpty()) Glide.with(context)
            .load(model.productPhoto)
            .thumbnail(0.1f)
            .placeholder(R.mipmap.ic_launcher)
            .error(R.mipmap.ic_launcher)
            .into(holder.mProductImageView)
        holder.mSelectedColorImageView.setColorFilter(
            Color.parseColor(model.selectedColor),
            PorterDuff.Mode.MULTIPLY
        )

        holder.mProductPageRelativeLayout.setOnClickListener {
            val intent = Intent(context, ProductDetailsActivity::class.java)
            intent.putExtra("Json", "Yes")
            intent.putExtra("name", model.name)
            intent.putExtra("description", model.description)
            intent.putExtra("regularPrice", model.regularPrice.toString())
            intent.putExtra("salePrice", model.salePrice.toString())
            intent.putExtra("productPhoto", model.productPhoto)
            intent.putExtra("selectedColor", model.selectedColor)
            context.startActivity(intent)
        }
        holder.mProductImageView.setOnClickListener {
            val intent = Intent(context, ImageActivity::class.java)
            intent.putExtra("image", model.productPhoto)
            intent.putExtra("title", model.name)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return if (null != mProductList) mProductList!!.size else 0
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var mTitleView: TextView
        var mDescriptionView: TextView
        var mSalePriceView: TextView
        var mRegularPriceView: TextView
        var mProductImageView: ImageView
        var mSelectedColorImageView: ImageView
        var mProductPageRelativeLayout: RelativeLayout

        init {
            mTitleView = view.findViewById(R.id.tv_product_title)
            mDescriptionView = view.findViewById(R.id.tv_product_description)
            mSalePriceView = view.findViewById(R.id.tv_sale_price)
            mRegularPriceView = view.findViewById(R.id.tv_regular_price)
            mProductImageView = view.findViewById(R.id.iv_product_image)
            mSelectedColorImageView = view.findViewById(R.id.iv_color)
            mProductPageRelativeLayout = view.findViewById(R.id.rl_product_page)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

}