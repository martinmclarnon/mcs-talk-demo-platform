package com.demo.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "blog")
data class BlogEntity(

    @Id
    val id: String,

    @Column(name = "post")
    val post: String

)
