package com.example.one.model.dataSource.comment

import com.example.one.model.dataClass.Comment

interface CommentDataSource {

    suspend fun getComments(productId:Int):List<Comment>
}