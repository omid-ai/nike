package com.example.one.ui.orderHistory

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.one.databinding.ItemOrderHistotyBinding
import com.example.one.model.dataClass.OrderHistoryItem
import com.example.one.service.ImageLoadingService
import com.example.one.service.NikeImageView
import com.example.one.util.UtilFunctions.convertDpToPixel
import com.example.one.util.UtilFunctions.formatPrice
import javax.inject.Inject

class OrderHistoryAdapter : RecyclerView.Adapter<OrderHistoryAdapter.ViewHolder>() {


    lateinit var binding: ItemOrderHistotyBinding
    var context:Context?=null
    val layoutParam:LinearLayout.LayoutParams
    var orderHistory:List<OrderHistoryItem>?=null

    init {
        val size=convertDpToPixel(100f,context).toInt()
        val margin= convertDpToPixel(8f,context).toInt()

        layoutParam=LinearLayout.LayoutParams(size,size)
        layoutParam.setMargins(margin,0,margin,0)
    }

    inner class ViewHolder(binding: ItemOrderHistotyBinding):RecyclerView.ViewHolder(binding.root){
        fun bindOrderHistory(orderHistoryItem: OrderHistoryItem){
            binding.orderIdTv.text=orderHistoryItem.id.toString()
            binding.payablePriceTv.text=formatPrice(orderHistoryItem.payable)
            binding.orderProductLinearLayout.removeAllViews()
            orderHistoryItem.order_items.forEach {
                val imageView=NikeImageView(context)
                imageView.layoutParams=layoutParam
                imageView.setImageURI(it.product.image)
                binding.orderProductLinearLayout.addView(imageView)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding= ItemOrderHistotyBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindOrderHistory(orderHistory!![position])
    }

    override fun getItemCount(): Int = orderHistory!!.size
}