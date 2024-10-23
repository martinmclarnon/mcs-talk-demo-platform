package com.demo.dto

import java.util.*

data class BookResponse(

    val id: String,

    val title: String,

    val isbn: String,

    val author: String,

    val publisher: String,

    val publishedDate: Date,

    val numberOfPages: Int,

    val languageWrittenIn: String,

    val review: String

)
