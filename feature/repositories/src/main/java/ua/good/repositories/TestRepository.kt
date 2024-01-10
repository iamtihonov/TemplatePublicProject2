package ua.good.repositories

import kotlinx.datetime.Clock
import ua.good.model.Language
import ua.good.model.Repository

fun getTestRepositories(): List<Repository> {
    return List(30) { i ->
        Repository(i, "Репозиторий $i", Language.KOTLIN, Clock.System.now())
    }
}
