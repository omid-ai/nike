package com.example.one.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.one.common.base.NikeFragment
import com.example.one.databinding.FragmentHomeBinding
import com.example.one.model.dataClass.Product
import com.example.one.ui.common.ProductListAdapter
import com.example.one.ui.list.ProductListActivity
import com.example.one.ui.main.banner.BannerSliderAdapter
import com.example.one.ui.productDetail.ProductDetailActivity
import com.example.one.util.EXTRA_KEY_PRODUCT_DATA
import com.example.one.util.EXTRA_KEY_PRODUCT_SORT
import com.example.one.util.UtilFunctions.convertDpToPixel
import com.example.one.util.Variables
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment: NikeFragment() {

    lateinit var binding: FragmentHomeBinding
    private val homeViewModel:HomeViewModel by viewModels()

    @Inject
    lateinit var adapter: ProductListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.newestProductRv.layoutManager=LinearLayoutManager(requireContext(),RecyclerView.HORIZONTAL,false)

        binding.viewAllNewestBtn.setOnClickListener {
            startActivity(Intent(requireContext(),ProductListActivity::class.java).apply {
                putExtra(EXTRA_KEY_PRODUCT_SORT,Variables.SORT_LATEST)
            })
        }

        homeViewModel.bannerLiveData.observe(viewLifecycleOwner){
            Timber.i("list of banners-> $it")
            val bannerAdapter=BannerSliderAdapter(this,it)
            binding.bannerSliderViewPagerHome.adapter=bannerAdapter
            calculateBannerSize()
            binding.sliderIndicator.setViewPager2(binding.bannerSliderViewPagerHome)
        }

        homeViewModel.productLiveData.observe(viewLifecycleOwner){
            Timber.i("list of products-> $it")
            adapter.products= it as ArrayList<Product>?
            binding.newestProductRv.adapter=adapter
        }

        adapter.onItemClicked={
            startActivity(Intent(requireContext(),ProductDetailActivity::class.java).apply {
                putExtra(EXTRA_KEY_PRODUCT_DATA,it)
            })
        }
    }

    override fun onResume() {
        super.onResume()
        calculateBannerSize()
    }

    private fun calculateBannerSize() {
        val height = ((binding.bannerSliderViewPagerHome.measuredWidth-(
                convertDpToPixel(
                    32f,
                    requireContext()
                )
                )) * 173) / 328
        val layoutParam=binding.bannerSliderViewPagerHome.layoutParams
        layoutParam.height=height.toInt()
        binding.bannerSliderViewPagerHome.layoutParams=layoutParam
    }
}