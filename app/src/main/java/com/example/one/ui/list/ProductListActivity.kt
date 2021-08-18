package com.example.one.ui.list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.one.databinding.ActivityProductListBinding

class ProductListActivity : AppCompatActivity() {

    lateinit var binding: ActivityProductListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityProductListBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}