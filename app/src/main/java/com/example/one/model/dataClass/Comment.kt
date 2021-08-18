package com.example.one.model.dataClass


data class Comment(
    val author: Author?=null,
    val content: String,
    val date: String?=null,
    val id: Int,
    val title: String
)