package com.example.one.ui.checkout

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.one.R
import com.example.one.databinding.ActivityCheckOutBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class CheckOutActivity : AppCompatActivity() {

    lateinit var binding: ActivityCheckOutBinding
    val viewModel: CheckOutViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckOutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getCheckOutDetailFromCodBuying()
        getCheckoutDetailFromOnlineBuying()
        getDataFromViewModel()

    }

    private fun getCheckOutDetailFromCodBuying() {
        viewModel.orderIdLiveData.observe(this) {
            viewModel.provideCheckOutDetail(it)
        }
    }

    private fun getCheckoutDetailFromOnlineBuying() {
        viewModel.orderIdLiveData.observe(this) {
            if (it == null) {
                val data: Uri? = intent.data
                data?.let { uri ->
                    val response = uri.getQueryParameter("order_id")!!.toInt()
                    viewModel.provideCheckOutDetail(response)
                }
            }
        }
    }

    private fun getDataFromViewModel() {
        viewModel.checkoutLiveData.observe(this) {
            Timber.i("checkout data-> $it")
            binding.orderPriceTv.text =it.payable_price.toString()
            binding.orderStatusTv.text = it.payment_status
            binding.checkoutTitleTv.text =
                if (it.purchase_success) getString(R.string.successAtBuying) else getString(R.string.failedAtBuying)
        }
    }
}
