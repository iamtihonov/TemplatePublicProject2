package ua.good.repositories

import ua.good.model.Repository
import ua.good.utils.base.ViewState

sealed class RepositoriesStates<out T> : ViewState {
    data object Default : RepositoriesStates<Nothing>()
    data object NeedLoaded : RepositoriesStates<Nothing>()
    data object Progress : RepositoriesStates<Nothing>()
    data class Loaded<out T> (val list: List<Repository>) : RepositoriesStates<T>()
    data object InternetError : RepositoriesStates<Nothing>()
    data object ServerError : RepositoriesStates<Nothing>()
}
