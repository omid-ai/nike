package com.example.one.ui.cart

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.one.R
import com.example.one.model.dataClass.CartItem
import com.example.one.model.dataClass.PurchaseDetail
import com.example.one.service.ImageLoadingService
import com.example.one.service.NikeImageView
import com.example.one.util.UtilFunctions.formatPrice
import com.example.one.util.Variables.CART_ITEM_VIEW_TYPE
import com.example.one.util.Variables.PURCHASE_DETAIL_VIEW_TYPE
import timber.log.Timber
import javax.inject.Inject

class CartAdapter @Inject constructor(
    val imageLoadingService: ImageLoadingService
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    var cartItems = ArrayList<CartItem>()
    fun setData(cartItems: List<CartItem>) {
        this.cartItems.clear()
        this.cartItems.addAll(cartItems)
        cartItems.let {
            Timber.i("adapter cart items-> $it")
        }
        notifyDataSetChanged()
    }

    var purchaseDetails: PurchaseDetail? = null


    var onIncreaseBtnClicked: ((CartItem) -> Unit)? = null
    var onDecreaseBtnClicked: ((CartItem) -> Unit)? = null
    var onRemoveBtnClicked: ((CartItem) -> Unit)? = null
    var onProductImageClicked: ((CartItem) -> Unit)? = null

    inner class CartItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val productIv = itemView.findViewById<NikeImageView>(R.id.productImageV)
        private val productName = itemView.findViewById<TextView>(R.id.productTitle)
        private val currentPriceTv = itemView.findViewById<TextView>(R.id.currentPrice)
        private val previousPriceTv = itemView.findViewById<TextView>(R.id.previousPrice)
        private val productCount = itemView.findViewById<TextView>(R.id.productCount)
        private val increaseItemCountIv = itemView.findViewById<ImageView>(R.id.increaseCountIv)
        private val decreaseItemCountIv = itemView.findViewById<ImageView>(R.id.decreaseCountIv)
        private val removeFromCart = itemView.findViewById<TextView>(R.id.removeFromCart)

        fun bindCartItem(cartItem: CartItem) {
            imageLoadingService.load(productIv, cartItem.product.image)
            productName.text = cartItem.product.title
            currentPriceTv.text = formatPrice(cartItem.product.price - cartItem.product.discount)
            previousPriceTv.text =
                formatPrice(cartItem.product.previous_price - cartItem.product.discount)
            previousPriceTv.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            productCount.text = cartItem.count.toString()

            if (cartItem.count>5){
                productCount.text= 5.toString()
            }

            increaseItemCountIv.setOnClickListener {
                onIncreaseBtnClicked?.invoke(cartItem)
            }

            decreaseItemCountIv.setOnClickListener {
                onDecreaseBtnClicked?.invoke(cartItem)
            }

            removeFromCart.setOnClickListener {
                onRemoveBtnClicked?.invoke(cartItem)
            }

            productIv.setOnClickListener {
                onProductImageClicked?.invoke(cartItem)
            }
        }
    }

    class PurchaseDetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val totalPriceTv = itemView.findViewById<TextView>(R.id.tvTotalPrice)
        private val payablePriceTv = itemView.findViewById<TextView>(R.id.tvPayablePrice)
        private val shippingCostTv = itemView.findViewById<TextView>(R.id.tvShippingCost)
        fun bindPurchaseDetail(totalPrice: Int, payablePrice: Int, shippingCost: Int) {
            totalPriceTv.text = formatPrice(totalPrice)
            payablePriceTv.text = formatPrice(payablePrice)
            shippingCostTv.text = formatPrice(shippingCost)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == CART_ITEM_VIEW_TYPE) {
            return CartItemViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
            )
        } else {
            return PurchaseDetailViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_purchase_detail, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CartItemViewHolder){
            holder.bindCartItem(cartItems[position])
        }else if (holder is PurchaseDetailViewHolder){
            purchaseDetails?.let {
                holder.bindPurchaseDetail(it.totalPrice,it.payablePrice,it.shippingCost)
            }
        }
    }

    override fun getItemCount(): Int = cartItems.size + 1

    override fun getItemViewType(position: Int): Int {
        //for showing a perfect recyclerView this is so important to this
        // part being written exactly like below
        if (position == cartItems.size)
            return PURCHASE_DETAIL_VIEW_TYPE
        else
            return CART_ITEM_VIEW_TYPE
    }

    fun onRemoveBtnClicked(cartItem: CartItem){
        val index=cartItems.indexOf(cartItem)
        if (index!=-1){
            cartItems.removeAt(index)
            notifyItemRemoved(index)
            notifyDataSetChanged()
        }
    }

    fun onChangeCartItemCount(cartItem: CartItem){
        val index=cartItems.indexOf(cartItem)
        if (index!=-1){
            notifyItemChanged(index)
        }
    }
}