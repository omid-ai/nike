package com.example.one.ui.orderHistory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.one.databinding.ActivityOrderHistoryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderHistoryActivity : AppCompatActivity() {

    lateinit var binding:ActivityOrderHistoryBinding
    val viewModel:OrderHistoryViewModel by viewModels()
    val adapter=OrderHistoryAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityOrderHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerViewOrderHistory.layoutManager=LinearLayoutManager(this,RecyclerView.VERTICAL,false)
        adapter.context=this

        viewModel.orderHistoryLiveData.observe(this){
            adapter.orderHistory=it
            binding.recyclerViewOrderHistory.adapter=adapter
        }
    }
}