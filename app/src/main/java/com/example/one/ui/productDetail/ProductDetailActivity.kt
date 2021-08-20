package com.example.one.ui.productDetail

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.one.R
import com.example.one.common.base.NikeActivity
import com.example.one.common.exceptionHandling.Result
import com.example.one.databinding.ActivityProductDetailBinding
import com.example.one.model.dataClass.Comment
import com.example.one.service.ImageLoadingService
import com.example.one.ui.productDetail.comment.CommentListAdapter
import com.example.one.ui.productDetail.comment.CommentsActivity
import com.example.one.util.EXTRA_KEY_PRODUCT_ID
import com.example.one.util.UtilFunctions.formatPrice
import com.example.one.view.observableScrollView.ObservableScrollViewCallbacks
import com.example.one.view.observableScrollView.ScrollState
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class ProductDetailActivity : NikeActivity() {

    lateinit var binding: ActivityProductDetailBinding
    val viewModel: ProductDetailViewModel by viewModels()
    val adapter=CommentListAdapter()

    @Inject
    lateinit var imageLoadingService: ImageLoadingService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.commentRv.layoutManager=LinearLayoutManager(this,RecyclerView.VERTICAL,false)

        viewModel.productDetailLiveData.observe(this) {
            imageLoadingService.load(binding.IvProduct, it.image)
            binding.productNameTv.text=it.title
            binding.currentPriceTv.text = formatPrice(it.price)
            binding.previousPriceTv.text = formatPrice(it.previous_price)
            binding.previousPriceTv.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            binding.TitleToolbarTv.text=it.title
        }

        binding.IvProduct.post {
            val height=binding.IvProduct.measuredHeight
            binding.observableScrollView.addScrollViewCallbacks(object :ObservableScrollViewCallbacks{
                override fun onScrollChanged(
                    scrollY: Int,
                    firstScroll: Boolean,
                    dragging: Boolean
                ) {
                    binding.toolbar.alpha=scrollY.toFloat()/height.toFloat()
                    binding.IvProduct.translationY=scrollY.toFloat()/2
                }

                override fun onDownMotionEvent() {

                }

                override fun onUpOrCancelMotionEvent(scrollState: ScrollState?) {

                }

            })
        }

        viewModel.commentLiveData.observe(this){
            Timber.i("list of comments-> $it")
            adapter.comments= it as ArrayList<Comment>
            binding.commentRv.adapter=adapter

            if (it.size>3){
                binding.viewAllCommentsBtn.visibility=View.VISIBLE
                binding.viewAllCommentsBtn.setOnClickListener {
                    startActivity(Intent(this,CommentsActivity::class.java).apply {
                        putExtra(EXTRA_KEY_PRODUCT_ID,viewModel.productDetailLiveData.value!!.id)
                    })
                }
            }
        }

        binding.addToCartBtn.setOnClickListener {
            viewModel.addToCart()
            viewModel.addToCartResultLiveData.observe(this){result->
                when(result){
                    is Result.Success->showSnackBar(getString(R.string.success_addToCart))
                }
            }
        }
    }
}