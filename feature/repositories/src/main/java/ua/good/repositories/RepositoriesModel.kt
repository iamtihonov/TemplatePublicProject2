package ua.good.repositories

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import ua.good.domain.IGetRepositoriesUseCase
import ua.good.model.RepositoriesFilter
import ua.good.model.Repository
import ua.good.utils.ResultWrapper
import ua.good.utils.base.BaseInternetModel
import javax.inject.Inject

/**
 * Презентер [RepositoriesScreen]
 * Желательно использовать SavedStateHandle для сохранения currentFilter, на Android 13
 * метод handleFilterChanged вызывается после повтора экрана
 *
 * https://proandroiddev.com/saving-ui-state-with-viewmodel-savedstate-and-dagger-f77bcaeb8b08
 * https://proandroiddev.com/viewmodels-state-persistence-savedstate-54d015acad82
 */
@HiltViewModel
class RepositoriesModel @Inject constructor(
    private var getRepositoriesUseCase: IGetRepositoriesUseCase
) : BaseInternetModel() {

    var state = MutableStateFlow<RepositoriesStates<List<Repository>>>(RepositoriesStates.Default)
    private var currentFilter = RepositoriesFilter.DEFAULT
    private var loadRepositoriesJob: Job? = null

    init {
        loadRepositoriesIfNeed(true)
    }

    private fun loadRepositoriesIfNeed(isInit: Boolean) {
        state.value = RepositoriesStates.Progress
        loadRepositoriesJob?.cancel()
        loadRepositoriesJob = launchOnIO {
            handleResult(getRepositoriesUseCase.invoke(currentFilter, isInit))
        }
    }

    @Suppress("CascadeIf")
    private fun handleResult(result: ResultWrapper<List<Repository>>) {
        if (result is ResultWrapper.Success) {
            state.value = RepositoriesStates.Loaded(result.value)
        } else if (result is ResultWrapper.NetworkError) {
            state.value = RepositoriesStates.InternetError
        } else if (result is ResultWrapper.ServerError) {
            state.value = RepositoriesStates.ServerError
        } else if (result is ResultWrapper.CanceledError) {
            state.value = RepositoriesStates.Default
        }
    }

    fun handleFilterChanged(value: RepositoriesFilter) {
        currentFilter = value
        state.value = RepositoriesStates.NeedLoaded
        loadRepositoriesIfNeed(false)
    }

    override fun retryButtonClicked() = loadRepositoriesIfNeed(false)
}
