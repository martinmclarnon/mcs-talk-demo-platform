package com.demo.service

import com.demo.dto.BlogResponse
import com.demo.dto.ListBlogRequest
import com.demo.persistence.entity.BlogEntity
import com.demo.persistence.repository.BlogRepository
import org.springframework.stereotype.Service
import org.springframework.data.domain.PageRequest
import java.util.stream.Collectors

@Service
class BlogServiceImpl (val blogRepository: BlogRepository) : BlogService {
    override fun list(listBlogRequest: ListBlogRequest): List<BlogResponse> {
        val page = blogRepository.findAll(PageRequest.of(listBlogRequest.page, listBlogRequest.size))
        val blogs: List<BlogEntity> = page.get().collect(Collectors.toList())
        return blogs.map { convertBlogToBlogResponse(it) }
    }

    private fun convertBlogToBlogResponse(blogEntity: BlogEntity): BlogResponse {
        return BlogResponse(
            id = blogEntity.id,
            post = blogEntity.post
        )
    }
}

