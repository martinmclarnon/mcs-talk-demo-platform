package com.demo.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*

@Entity
@Table(name = "book")
data class Book(

    @Id
    val id: String,

    @Column(name = "title")
    val title: String,

    @Column(name = "isbn")
    val isbn: String,

    @Column(name = "author")
    val author: String,

    @Column(name = "publisher")
    val publisher: String,

    @Column(name = "published_date")
    val publishedDate: Date,

    @Column(name = "number_of_pages")
    val numberOfPages: Int,

    @Column(name = "language_written_in")
    val languageWrittenIn: String,

    @Column(name = "review")
    val review: String

)
