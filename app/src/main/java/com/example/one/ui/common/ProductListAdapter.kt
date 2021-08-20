package com.example.one.ui.common

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.one.R
import com.example.one.model.dataClass.Product
import com.example.one.service.ImageLoadingService
import com.example.one.service.NikeImageView
import com.example.one.util.UtilFunctions.formatPrice
import com.example.one.util.Variables
import javax.inject.Inject

class ProductListAdapter @Inject constructor(
    private val imageLoadingService: ImageLoadingService
) : RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {

    var products:ArrayList<Product>?=null
    var viewType:Int=Variables.VIEW_TYPE_ROUNDED

    var onItemClicked: ((Product) -> Unit)? = null


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val currentPriceTv: TextView = itemView.findViewById(R.id.currentPriceTv)
        private val previousPriceTv: TextView = itemView.findViewById(R.id.previousPriceTv)
        private val productIv: NikeImageView = itemView.findViewById(R.id.productIv)
        private val productName: TextView = itemView.findViewById(R.id.productNameTv)
        private val favoriteBtn: ImageView = itemView.findViewById(R.id.favoriteBtn)
        fun bindProduct(product: Product) {
            imageLoadingService.load(productIv, product.image)
            productName.text = product.title
            currentPriceTv.text = formatPrice(product.price)
            previousPriceTv.text = formatPrice(product.previous_price)
            previousPriceTv.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG

            itemView.setOnClickListener {
                onItemClicked?.invoke(product)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val layoutRes=when(viewType){
            Variables.VIEW_TYPE_ROUNDED->R.layout.item_product
            Variables.VIEW_TYPE_LARGE->R.layout.item_product_large
            Variables.VIEW_TYPE_SMALL->R.layout.item_product_small
            else->throw IllegalStateException("unknown view type")
        }

        return ViewHolder(LayoutInflater.from(parent.context).inflate(layoutRes,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindProduct(products!![position])
    }

    override fun getItemCount(): Int = products!!.size

    override fun getItemViewType(position: Int): Int {
        return viewType
    }
}