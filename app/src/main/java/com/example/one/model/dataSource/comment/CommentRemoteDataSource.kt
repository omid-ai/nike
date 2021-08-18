package com.example.one.model.dataSource.comment

import com.example.one.model.apiService.NikeApiService
import com.example.one.model.dataClass.Comment
import javax.inject.Inject

class CommentRemoteDataSource @Inject constructor(
    private val apiService: NikeApiService
):CommentDataSource {
    override suspend fun getComments(productId: Int): List<Comment> =
        apiService.fetchComments(productId)
}