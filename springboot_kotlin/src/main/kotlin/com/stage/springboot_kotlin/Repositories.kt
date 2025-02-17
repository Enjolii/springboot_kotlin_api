package com.stage.springboot_kotlin

import org.springframework.data.jpa.repository.JpaRepository

interface ArticleRepository : JpaRepository<Article, Long> {
    fun findAllByOrderByCreatedAtDesc(): List<Article>
    fun findBySlug(slug: String): Article?
}