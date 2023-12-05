package ua.good.data.repositories

import ua.good.data.mappers.IRepositoryMapper
import ua.good.db.RepositoryDao
import ua.good.model.Repository
import ua.good.network.helper.IApiHelper
import ua.good.utils.ResultWrapper
import ua.good.utils.base.BaseDataManager
import javax.inject.Inject

abstract class IRepositoriesManager : BaseDataManager() {
    abstract suspend fun getRepositories(useCache: Boolean): ResultWrapper<List<Repository>>
    abstract suspend fun loadRepositories(): ResultWrapper<List<Repository>>
}

internal class RepositoriesManager @Inject constructor(
    private val apiHelperProvider: () -> IApiHelper,
    private val repositoryDaoProvider: () -> RepositoryDao,
    private val repositoryMapperProvider: () -> IRepositoryMapper
) : IRepositoriesManager() {
    override suspend fun getRepositories(useCache: Boolean): ResultWrapper<List<Repository>> {
        if (useCache) {
            val cache = repositoryDaoProvider().getRepositories()
            if (cache.isNotEmpty()) {
                return ResultWrapper.Success(repositoryMapperProvider().mapFromModel(cache))
            }
        }

        return loadRepositories()
    }

    override suspend fun loadRepositories(): ResultWrapper<List<Repository>> {
        val result = apiHelperProvider().loadUserRepositories("test_token")
        return if (result is ResultWrapper.Success) {
            val dbRepositories = repositoryMapperProvider().mapToModel(result.value)
            repositoryDaoProvider().update(dbRepositories)
            ResultWrapper.Success(repositoryMapperProvider().mapFromModel(dbRepositories))
        } else {
            result.getError()
        }
    }
}
