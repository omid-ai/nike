package com.example.one.model.repository.comment

import com.example.one.model.dataClass.Comment
import com.example.one.model.dataSource.comment.CommentDataSource
import javax.inject.Inject

class CommentRepositoryImpl @Inject constructor(
    private val remoteDataSource: CommentDataSource
):CommentRepository {
    override suspend fun getComments(productId: Int): List<Comment> =
        remoteDataSource.getComments(productId)
}