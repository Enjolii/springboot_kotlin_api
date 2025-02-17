package com.stage.springboot_kotlin

import jakarta.persistence.*
import jdk.jfr.Name
import java.time.LocalDateTime

@Entity
@Name("articles")
class Article(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var title: String,
    var content: String,
    var createdAt: LocalDateTime = LocalDateTime.now(),
    var slug: String = title.toSlug(),
)