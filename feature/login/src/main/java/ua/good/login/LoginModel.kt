package ua.good.login

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
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

    val progressState = MutableStateFlow(LoginProgressState.DEFAULT)
    val dialogStates = MutableStateFlow(LoginDialogStates.NOTHING)
    val login = MutableStateFlow("")
    val password = MutableStateFlow("")

    private var viewModelJob: Job? = null

    init {
        progressState.value = LoginProgressState.PROGRESS
        launchOnIO {
            loginUseCase.clearAuthorizedData()
            val login = loginUseCase.geCurrentUserLogin()
            updateLoginData(LoginShowedData(login))
        }
    }

    private fun updateLoginData(value: LoginShowedData) {
        launchOnUI {
            login.value = value.login
            password.value = value.password
            progressState.value = LoginProgressState.DEFAULT
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
            progressState.value = LoginProgressState.PROGRESS
            viewModelJob = launchOnIO {
                handleResult(loginUseCase.login(login.value, password.value))
            }
        } else {
            dialogStates.value = LoginDialogStates.IS_EMPTY_DATA_ERROR
        }
    }

    private fun handleResult(result: ResultWrapper<Boolean>) {
        progressState.value = LoginProgressState.DEFAULT
        val event = when (result) {
            is ResultWrapper.NetworkError -> LoginDialogStates.IS_INTERNET_ERROR
            is ResultWrapper.ServerError -> LoginDialogStates.IS_UNKNOWN_ERROR
            is ResultWrapper.AuthorizedError -> LoginDialogStates.IS_ERROR_LOGIN_DATA
            is ResultWrapper.Success -> LoginDialogStates.SUCCESS
            else -> LoginDialogStates.NOTHING
        }
        dialogStates.value = event
    }

    fun eventFinished() {
        dialogStates.value = LoginDialogStates.NOTHING
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob?.cancel()
    }
}
