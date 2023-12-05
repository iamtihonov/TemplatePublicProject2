package ua.good.model

import kotlinx.datetime.Instant

data class Repository(
    var id: Int = 0,
    var name: String,
    var language: Language,
    var instant: Instant
)

enum class Language {
    JAVA,
    KOTLIN,
    OTHER
}
