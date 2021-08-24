package com.example.one.ui.list

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.one.R
import com.example.one.common.base.NikeActivity
import com.example.one.databinding.ActivityProductListBinding
import com.example.one.model.dataClass.Product
import com.example.one.ui.common.ProductListAdapter
import com.example.one.ui.productDetail.ProductDetailActivity
import com.example.one.util.EXTRA_KEY_PRODUCT_DATA
import com.example.one.util.Variables
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class ProductListActivity : NikeActivity() {

    lateinit var binding: ActivityProductListBinding
    val viewModel:ProductListViewModel by viewModels()

    @Inject
    lateinit var adapter:ProductListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityProductListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.progressBarLiveData.observe(this){
            showProgressBar(it)
        }

        val gridLayout=GridLayoutManager(this,2)
        binding.productsRv.layoutManager=gridLayout

        viewModel.onSortChangedByUser(viewModel.sortState.value!!)

        viewModel.selectedSortTitleLiveData.observe(this){
            binding.selectedTitleNameTv.text=getString(it)
        }

        viewModel.productListLiveData.observe(this){
            Timber.i("list of products-> $it")
            adapter.products= it as ArrayList<Product>?
            adapter.viewType=Variables.VIEW_TYPE_SMALL
            binding.productsRv.adapter=adapter
        }

        binding.viewTypeChangerBtn.setOnClickListener {
            if (adapter.viewType==Variables.VIEW_TYPE_SMALL){
                adapter.viewType=Variables.VIEW_TYPE_LARGE
                gridLayout.spanCount=1
                binding.viewTypeChangerBtn.setImageResource(R.drawable.ic_grid)
                adapter.notifyDataSetChanged()
            }else{
                adapter.viewType=Variables.VIEW_TYPE_SMALL
                gridLayout.spanCount=2
                binding.viewTypeChangerBtn.setImageResource(R.drawable.ic_view_type_large)
                adapter.notifyDataSetChanged()
            }
        }

        adapter.onItemClicked={
            startActivity(Intent(this,ProductDetailActivity::class.java).apply {
                putExtra(EXTRA_KEY_PRODUCT_DATA,it)
            })
        }

        binding.sortBtn.setOnClickListener {
            val setSortMessage=MaterialAlertDialogBuilder(this).setSingleChoiceItems(R.array.sort,viewModel.sortState.value!!
            ) { dialog, which -> viewModel.onSortChangedByUser(which)
                dialog.dismiss()
            }.setTitle(R.string.sort)
                .show()
        }
    }
}