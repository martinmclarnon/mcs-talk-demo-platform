package com.demo.persistence.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "audit")
data class Audit(
    @Id val id: String? = null,
    val rawPayload:  Map<String, Any>
)