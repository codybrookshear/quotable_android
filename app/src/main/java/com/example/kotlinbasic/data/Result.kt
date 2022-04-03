package com.example.kotlinbasic.data

data class Result (
    val _id: String,
    val author: String,
    val content: String,
    val authorSlug: String,
    val length: Int,
    val dateAdded: String,
    val dateModified: String,
    val tags: List<String>
)