package ua.good.domain

import ua.good.data.repositories.IRepositoriesManager
import ua.good.model.Language
import ua.good.model.RepositoriesFilter
import ua.good.model.Repository
import ua.good.utils.ResultWrapper
import javax.inject.Inject

interface IGetRepositoriesUseCase {
    suspend fun invoke(filter: RepositoriesFilter, isInit: Boolean): ResultWrapper<List<Repository>>
}

internal class GetRepositoriesUseCase @Inject constructor(
    private val repositoryManager: IRepositoriesManager,
    private val loginUseCase: ILoginUseCase
) : IGetRepositoriesUseCase {

    override suspend fun invoke(filter: RepositoriesFilter, isInit: Boolean): ResultWrapper<List<Repository>> {
        var result = repositoryManager.getRepositories(!isInit)
        if (result is ResultWrapper.AuthorizedError && loginUseCase.relogin().isSuccess()) {
            result = repositoryManager.getRepositories(!isInit)
        }
        return if (result is ResultWrapper.Success) {
            ResultWrapper.Success(filter(result.value, filter))
        } else {
            result
        }
    }

    private fun filter(values: List<Repository>, filter: RepositoriesFilter): List<Repository> {
        return values.filter {
            filter == RepositoriesFilter.DEFAULT ||
                filter == RepositoriesFilter.JAVA && it.language == Language.JAVA ||
                filter == RepositoriesFilter.KOTLIN && it.language == Language.KOTLIN
        }
    }
}
