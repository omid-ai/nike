package com.example.one.ui.productDetail.comment

import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.one.common.base.NikeActivity
import com.example.one.databinding.ActivityCommentsBinding
import com.example.one.model.dataClass.Comment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommentsActivity : NikeActivity() {

    lateinit var binding: ActivityCommentsBinding
    val viewModel:CommentsViewModel by viewModels()
    val adapter=CommentListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityCommentsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.commentsRv.layoutManager=LinearLayoutManager(this,RecyclerView.VERTICAL,false)

        viewModel.commentsLiveData.observe(this){
            adapter.comments= it as ArrayList<Comment>
            if (it.size>3){
                adapter.showAll=true
                binding.commentsRv.adapter=adapter
            }
        }
    }
}