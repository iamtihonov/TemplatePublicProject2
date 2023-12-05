package ua.good.login

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ua.good.domain.ILoginUseCase
import ua.good.utils.ResultWrapper
import ua.good.utils.base.BaseModel
import javax.inject.Inject

/**
 * Модель [LoginScreen]
 * В идеале к примеру после нажатии у диалога кнопки ok, возвращать состояние
 * Использовать одно поле для state плохо (в этом случае будет излишнее обновление полей с данными)
 */
@HiltViewModel
class LoginModel @Inject constructor(
    private val loginUseCase: ILoginUseCase
) : BaseModel() {

    val state = MutableStateFlow(LoginProgressState.DEFAULT)
    val events = MutableStateFlow(LoginEvents.NOTHING)
    val login = MutableStateFlow("")
    val password = MutableStateFlow("")

    private var viewModelJob: Job? = null

    init {
        state.value = LoginProgressState.PROGRESS
        CoroutineScope(Dispatchers.IO).launch {
            loginUseCase.clearAuthorizedData()
            val login = loginUseCase.geCurrentUserLogin()
            updateLoginData(LoginShowedData(login))
        }
    }

    private fun updateLoginData(value: LoginShowedData) {
        launchOnUI {
            login.value = value.login
            password.value = value.password
            state.value = LoginProgressState.DEFAULT
        }
    }

    fun loginChanged(value: String) {
        login.value = value
    }

    fun passwordChanged(value: String) {
        password.value = value
    }

    fun buttonLoginClicked() {
        if (viewModelJob?.isActive == true) {
            return
        } else if (login.value.isNotEmpty() && password.value.isNotEmpty()) {
            state.value = LoginProgressState.PROGRESS
            viewModelJob = CoroutineScope(Dispatchers.IO).launch {
                handleResult(loginUseCase.login(login.value, password.value))
            }
        } else {
            events.value = LoginEvents.IS_EMPTY_DATA_ERROR
        }
    }

    private fun handleResult(result: ResultWrapper<Boolean>) {
        state.value = LoginProgressState.DEFAULT
        val event = when (result) {
            is ResultWrapper.NetworkError -> LoginEvents.IS_INTERNET_ERROR
            is ResultWrapper.ServerError -> LoginEvents.IS_UNKNOWN_ERROR
            is ResultWrapper.AuthorizedError -> LoginEvents.IS_ERROR_LOGIN_DATA
            is ResultWrapper.Success -> LoginEvents.SUCCESS
            else -> LoginEvents.NOTHING
        }
        events.value = event
    }

    fun eventFinished() {
        events.value = LoginEvents.NOTHING
    }

    override fun onBackPressed() {
        state.value = LoginProgressState.DEFAULT
        if (viewModelJob?.isActive == true) {
            viewModelJob?.cancel()
        } else {
            events.value = LoginEvents.SCREEN_HIDED
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob?.cancel()
    }
}
