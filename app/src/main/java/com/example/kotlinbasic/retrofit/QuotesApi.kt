package com.example.kotlinbasic.retrofit

import com.example.kotlinbasic.data.QuoteList
import retrofit2.Response
import retrofit2.http.GET

interface QuotesApi {
    @GET("/quotes")
    suspend fun getQuotes() : Response<QuoteList>

}