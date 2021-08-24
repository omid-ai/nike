package com.example.one.ui.shipping

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.example.one.R
import com.example.one.common.base.NikeActivity
import com.example.one.databinding.ActivityShippingBinding
import com.example.one.ui.cart.CartAdapter
import com.example.one.util.Variables.PAYMENT_METHOD_COD
import com.example.one.util.Variables.PAYMENT_METHOD_ONLINE
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShippingActivity : NikeActivity() {

    lateinit var binding: ActivityShippingBinding
    val viewModel: ShippingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShippingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val purchaseDetailView = findViewById<View>(R.id.purchaseDetail)
        val viewHolder = CartAdapter.PurchaseDetailViewHolder(purchaseDetailView)

        viewModel.purchaseDetailLiveData.observe(this) {
            viewHolder.bindPurchaseDetail(it.totalPrice, it.payablePrice, it.shippingCost)
        }

        val onClickListener = View.OnClickListener {
            viewModel.submitOrders(
                binding.firstNameEt.text.toString(),
                binding.lastNameEt.text.toString(),
                binding.phoneNumberEt.text.toString(),
                binding.postalCodeEt.text.toString(),
                binding.addressEt.text.toString(),
                if (it.id == R.id.codBtn) PAYMENT_METHOD_COD else PAYMENT_METHOD_ONLINE,
                this
            )
        }

        val codBtn=findViewById<MaterialButton>(R.id.codBtn)
        codBtn.setOnClickListener(onClickListener)

        val onlinePaymentBtn=findViewById<MaterialButton>(R.id.onlinePayBtn)
        onlinePaymentBtn.setOnClickListener(onClickListener)
    }
}