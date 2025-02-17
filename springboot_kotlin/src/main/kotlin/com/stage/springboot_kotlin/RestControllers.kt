package com.stage.springboot_kotlin

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/articles")
class ArticleController(private val repository: ArticleRepository) {

    @GetMapping
    fun getAllArticles(): List<Article> = repository.findAllByOrderByCreatedAtDesc()

    @GetMapping("/{slug}")
    fun getArticleBySlug(@PathVariable slug: String): Article =
        repository.findBySlug(slug) ?: throw ResponseStatusException(
            HttpStatus.NOT_FOUND, "No article found with title $slug"
        )

    @PostMapping
    fun createArticle(@RequestBody article: Article): ResponseEntity<Article> {
        if (repository.findBySlug(article.slug) != null) {
            throw ResponseStatusException(HttpStatus.CONFLICT, "An article with this slug already exists")
        }
        article.id = null
        val savedArticle = repository.save(article)
        return ResponseEntity.status(HttpStatus.CREATED).body(savedArticle)
    }

    @PutMapping("/{slug}")
    fun updateArticle(@PathVariable slug: String, @RequestBody updatedArticle: Article): ResponseEntity<Article> {
        val existingArticle = repository.findBySlug(slug)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "No article found with title $slug")

        existingArticle.title = updatedArticle.title
        existingArticle.content = updatedArticle.content

        val savedArticle = repository.save(existingArticle)
        return ResponseEntity.ok(savedArticle)
    }

    @DeleteMapping("/{slug}")
    fun deleteArticle(@PathVariable slug: String): ResponseEntity<Void> {
        val existingArticle = repository.findBySlug(slug)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "No article found with title $slug")

        repository.delete(existingArticle)
        return ResponseEntity.noContent().build()
    }
}