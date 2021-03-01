package com.onuryahsi.newsapp.model

data class ErrorResponse(
    val status: String,
    val code: String,
    val message: String
)