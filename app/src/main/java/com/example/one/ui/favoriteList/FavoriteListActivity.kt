package com.example.one.ui.favoriteList

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.one.databinding.ActivityFavoriteListBinding
import com.example.one.model.dataClass.Product
import com.example.one.ui.productDetail.ProductDetailActivity
import com.example.one.util.EXTRA_KEY_PRODUCT_DATA
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteListActivity : AppCompatActivity() {

    lateinit var binding:ActivityFavoriteListBinding
    val viewModel:FavoriteListViewModel by viewModels()

    @Inject
    lateinit var adapter:FavoriteListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityFavoriteListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.favoriteListRv.layoutManager=LinearLayoutManager(this,RecyclerView.VERTICAL,false)

        onFavoriteProductClicked()
        onFavoriteProductLongClicked()
        getFavoriteProducts()
    }

    private fun getFavoriteProducts(){
        viewModel.favoriteProductsLiveData.observe(this){
            adapter.products= it as MutableList<Product>?
            binding.favoriteListRv.adapter=adapter
        }
    }


    private fun onFavoriteProductClicked(){
        adapter.onProductClicked={
            startActivity(Intent(this,ProductDetailActivity::class.java).apply {
                putExtra(EXTRA_KEY_PRODUCT_DATA,it)
            })
        }
    }

    private fun onFavoriteProductLongClicked(){
        adapter.onProductLongClicked={
            viewModel.deleteProductFromFavoriteList(it)
        }
    }
}