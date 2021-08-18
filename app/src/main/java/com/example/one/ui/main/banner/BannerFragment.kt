package com.example.one.ui.main.banner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.one.databinding.FragmentBannerBinding
import com.example.one.model.dataClass.Banner
import com.example.one.service.ImageLoadingService
import com.example.one.util.EXTRA_KEY_BANNER_DATA
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BannerFragment : Fragment() {

    lateinit var binding: FragmentBannerBinding

    @Inject
    lateinit var imageLoadingService: ImageLoadingService

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBannerBinding.inflate(inflater, container, false)
        val image = binding.root
        val banners = requireArguments().getParcelable<Banner>(EXTRA_KEY_BANNER_DATA)
            ?: throw IllegalStateException("can't be null")
        imageLoadingService.load(image, banners.image)
        return image
    }

    companion object {
        fun newInstance(banner: Banner): BannerFragment {
            return BannerFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(EXTRA_KEY_BANNER_DATA, banner)
                }
            }
        }
    }
}