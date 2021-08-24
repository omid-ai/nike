package com.example.one.ui.favoriteList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.one.databinding.ItemFavoriteProductBinding
import com.example.one.model.dataClass.Product
import com.example.one.service.ImageLoadingService
import javax.inject.Inject

class FavoriteListAdapter @Inject constructor(
    private val imageLoadingService: ImageLoadingService
) : RecyclerView.Adapter<FavoriteListAdapter.ViewHolder>() {

    lateinit var binding: ItemFavoriteProductBinding
    var products:MutableList<Product>?=null

    var onProductClicked: ((Product) -> Unit)? = null
    var onProductLongClicked: ((Product) -> Unit)? = null

    inner class ViewHolder(binding: ItemFavoriteProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindFavoriteProduct(product: Product) {
            imageLoadingService.load(binding.favoriteProductIv, product.image)
            binding.favoriteProductTv.text = product.title

            itemView.setOnClickListener {
                onProductClicked?.invoke(product)
            }

            itemView.setOnLongClickListener {
                onProductLongClicked?.invoke(product)
                products!!.remove(product)
                notifyItemRemoved(adapterPosition)
                return@setOnLongClickListener false
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding= ItemFavoriteProductBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindFavoriteProduct(products!![position])
    }

    override fun getItemCount(): Int =
        products!!.size
}