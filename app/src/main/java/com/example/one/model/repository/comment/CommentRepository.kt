package com.example.one.model.repository.comment

import com.example.one.model.dataClass.Comment

interface CommentRepository {

    suspend fun getComments(productId:Int):List<Comment>
}