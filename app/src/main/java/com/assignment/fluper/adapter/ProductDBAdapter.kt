package com.assignment.fluper.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.assignment.fluper.R
import com.assignment.fluper.databinding.CartProductDbBinding
import com.assignment.fluper.db.ProductEntity
import com.bumptech.glide.Glide

/**
 * @ProductDBAdapter is intended for set the list of products from database
 *
 * @author  Sushant Kumar
 */
class ProductDBAdapter(
    private val context: Context, val productEntityList: List<ProductEntity>,
    private val clickListener: (ProductEntity) -> Unit, private val clickListenerImage:
        (ProductEntity) -> Unit
) : RecyclerView.Adapter
<ProductDBAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        val binding : CartProductDbBinding =
            DataBindingUtil.inflate(
                layoutInflater, R.layout.cart_product_db, viewGroup,
                false
            )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder, @SuppressLint("RecyclerView")
        position: Int
    ) {
        holder.bind(productEntityList[position], clickListener, clickListenerImage, context)
    }

    override fun getItemCount(): Int {
        return productEntityList.size
    }

    class ViewHolder(val binding: CartProductDbBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            productEntity: ProductEntity,
            clickListener: (ProductEntity) -> Unit,
            clickListenerImage:
                (ProductEntity) -> Unit,
            context: Context
        ){
            binding.tvProductTitle.text = productEntity.name
            binding.tvProductDescription.text = productEntity.description
            binding.tvRegularPrice.text = context.getString(R.string.txt_rupee_sumbol) + productEntity.regular_price
            binding.tvRegularPrice.setPaintFlags(binding.tvRegularPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
            binding.tvSalePrice.text = context.getString(R.string.txt_rupee_sumbol) + productEntity.sale_price
            if (!productEntity.product_photo.isEmpty()) Glide.with(context)
            .load(productEntity.product_photo)
            .thumbnail(0.1f)
            .placeholder(R.mipmap.ic_launcher)
            .error(R.mipmap.ic_launcher)
            .into(binding.ivProductImage)
            binding.ivColor.setColorFilter(
                Color.parseColor(productEntity.selected_color),
                PorterDuff.Mode.MULTIPLY
            )
            binding.rlProductPage.setOnClickListener{
                clickListener(productEntity)
            }

            binding.ivProductImage.setOnClickListener{
                clickListenerImage(productEntity)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }


}