package com.example.one.ui.cart

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.one.R
import com.example.one.common.base.NikeFragment
import com.example.one.databinding.FragmentCartBinding
import com.example.one.ui.auth.AuthActivity
import com.example.one.ui.productDetail.ProductDetailActivity
import com.example.one.ui.shipping.ShippingActivity
import com.example.one.util.EXTRA_KEY_CART_ITEMS_PURCHASE_DETAIL
import com.example.one.util.EXTRA_KEY_PRODUCT_DATA
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class CartFragment : NikeFragment() {

    lateinit var binding: FragmentCartBinding
    val viewModel: CartViewModel by viewModels()
//    val adapter=CartAdapter()

    @Inject
    lateinit var adapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.progressBarLiveData.observe(viewLifecycleOwner){
            showProgressBar(it)
        }

        binding.cartRv.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

        viewModel.emptyStateLiveData.observe(viewLifecycleOwner) {
            val emptyState = showEmptyState(R.layout.item_cart_empty_state)
            if (it.showEmptyStatePage) {
                emptyState?.let {view ->
                    val emptyStateMessage = view.findViewById<TextView>(R.id.emptyStateMessageTv)
                    emptyStateMessage?.text =getString(it.emptyStateMessage)
                    val emptyStateCtaBtn =
                        view.findViewById<MaterialButton>(R.id.emptyStateCtaBtn)
                    emptyStateCtaBtn?.visibility = if (it.showCtaBtn) View.VISIBLE else View.INVISIBLE
                    emptyStateCtaBtn?.setOnClickListener {
                        requireActivity().startActivity(
                            Intent(
                                requireContext(),
                                AuthActivity::class.java
                            )
                        )
                    }
                }
            }
        }

        binding.payBtn.setOnClickListener {
            startActivity(Intent(requireContext(),ShippingActivity::class.java).apply {
                putExtra(EXTRA_KEY_CART_ITEMS_PURCHASE_DETAIL,viewModel.purchaseDetailsLiveData.value)
            })
        }

        viewModel.cartItemLiveData.observe(viewLifecycleOwner) {
            Timber.i("list of cart items-> $it")
            adapter.setData(it)
            adapter.notifyDataSetChanged()
        }

        viewModel.purchaseDetailsLiveData.observe(viewLifecycleOwner) {
            Timber.i("cart items purchase detail-> $it")
            adapter.purchaseDetails = it
            adapter.notifyDataSetChanged()
        }

        binding.cartRv.adapter = adapter

        adapter.onRemoveBtnClicked = {
            viewModel.removeItemFromCart(it)
            adapter.onRemoveBtnClicked(it)
        }

        adapter.onProductImageClicked = { cartItem ->
            requireActivity().startActivity(
                Intent(
                    requireContext(),
                    ProductDetailActivity::class.java
                ).apply {
                    putExtra(EXTRA_KEY_PRODUCT_DATA, cartItem.product)
                })
        }

        adapter.onIncreaseBtnClicked = {
            viewModel.increaseCartItemCount(it)
            adapter.onChangeCartItemCount(it)
        }

        adapter.onDecreaseBtnClicked = {
            viewModel.decreaseCartItemCount(it)
            adapter.onChangeCartItemCount(it)
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.refreshCart()
    }
}