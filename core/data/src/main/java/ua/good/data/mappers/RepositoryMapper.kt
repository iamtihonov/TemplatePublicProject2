package ua.good.data.mappers

import ua.good.db.content.RepositoryDataModel
import ua.good.model.Language
import ua.good.model.Repository
import ua.good.network.response.RepositoryResponse
import ua.good.utils.base.BaseMapper

abstract class IRepositoryMapper : BaseMapper<RepositoryResponse, RepositoryDataModel, Repository>()

internal class RepositoryMapper : IRepositoryMapper() {
    override fun mapToModel(input: RepositoryResponse) = RepositoryDataModel(
        id = input.id,
        name = input.name,
        language = input.language ?: "",
        instant = input.instant
    )

    override fun mapFromModel(input: RepositoryDataModel) = Repository(
        id = input.id,
        name = input.name,
        language = when (input.language) {
            "Java" -> Language.JAVA
            "Kotlin" -> Language.KOTLIN
            else -> Language.OTHER
        },
        instant = input.instant
    )
}
