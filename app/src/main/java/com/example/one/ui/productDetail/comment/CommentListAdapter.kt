package com.example.one.ui.productDetail.comment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.one.databinding.ItemCommentBinding
import com.example.one.model.dataClass.Comment

class CommentListAdapter(var showAll:Boolean=false): RecyclerView.Adapter<CommentListAdapter.ViewHolder>() {

    lateinit var binding: ItemCommentBinding
    var comments=ArrayList<Comment>()

    inner class ViewHolder(binding: ItemCommentBinding):RecyclerView.ViewHolder(binding.root){
        fun bindComments(comment: Comment){
            binding.userEmailTv.text=comment.author!!.email
            binding.commentContentTv.text=comment.content
            binding.commentDateTv.text=comment.date
            binding.commentTitleTv.text=comment.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding= ItemCommentBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindComments(comments[position])
    }

    override fun getItemCount(): Int =if (comments.size>3 && showAll)comments.size else 3
}