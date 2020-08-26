package com.assignment.fluper.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import com.assignment.fluper.R
import com.assignment.fluper.interfaces.IColorUpdate
import com.assignment.fluper.model.ColorModel

/**
 * @ColorsAdapter is intended for set the list of colors
 *
 * @author  Sushant Kumar
 */
class ColorsAdapter(private val mColorList: List<ColorModel>?,iColorUpdate: IColorUpdate) :
    RecyclerView.Adapter<ColorsAdapter.ViewHolder>() {
    private val iColorUpdates: IColorUpdate?=iColorUpdate
    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.color_item, viewGroup, false)
        return ViewHolder(v)
    }


    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView")
    position: Int) {
        val model = mColorList!![position]
        holder.mColorImageView.setColorFilter(
            Color.parseColor(model.colorCode),
            PorterDuff.Mode.MULTIPLY
        )
        if (model.isSelected) {
            holder.mSelectedImageView.visibility = View.VISIBLE
        } else {
            holder.mSelectedImageView.visibility = View.GONE
        }
        holder.mColorRelativeLayout.setOnClickListener {
            setUnSelect(position)
            iColorUpdates?.updateColorCode()
        }
    }

    override fun getItemCount(): Int {
        return mColorList?.size ?: 0
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var mSelectedImageView: ImageView
        var mColorImageView: ImageView
        var mColorRelativeLayout: RelativeLayout

        init {
            mSelectedImageView = view.findViewById(R.id.iv_selected)
            mColorImageView = view.findViewById(R.id.iv_color)
            mColorRelativeLayout = view.findViewById(R.id.rl_color)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    /**
     * setUnSelect is intended for set un select the option
     *
     * @param position
     *
     *The item un select position
     */
    private fun setUnSelect(position: Int) {
        for (e in mColorList!!.indices) {
            if (e == position) mColorList[e].isSelected = true else {
                mColorList[e].isSelected = false
            }
        }
        notifyDataSetChanged()
    }
}