package com.demo.persistence.repository

import com.demo.persistence.entity.BlogEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BlogRepository : JpaRepository<BlogEntity, String> {
    override fun findAll(): List<BlogEntity>
}