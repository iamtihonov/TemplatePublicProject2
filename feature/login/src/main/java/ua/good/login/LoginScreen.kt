package ua.good.login

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ua.good.utils.base.compose.colorButtonDefault
import ua.good.utils.base.compose.colorPrimary
import ua.good.utils.base.compose.colorPrimaryDark
import ua.good.utils.base.compose.widget.CustomTextField
import ua.good.utils.base.compose.widget.OkDialog

@Composable
fun LoginRoute(showRepositoriesEvent: () -> Unit, onBackClick: () -> Unit) {
    val model: LoginModel = hiltViewModel()
    val login by model.login.collectAsStateWithLifecycle()
    val password by model.password.collectAsStateWithLifecycle()
    val state by model.progressState.collectAsStateWithLifecycle()
    val events by model.dialogStates.collectAsStateWithLifecycle()

    val uiEvents = LoginActions(
        showRepositoriesEvent,
        { model.eventFinished() },
        onBackClick,
        { model.loginChanged(it) },
        { model.passwordChanged(it) },
        { model.buttonLoginClicked() }
    )

    LoginScreen(
        uiEvents,
        LoginAndPassword(login, password),
        state,
        events
    )
}

@Composable
fun LoginScreen(
    actions: LoginActions = LoginActions(),
    data: LoginAndPassword = LoginAndPassword(),
    progressState: LoginProgressState = LoginProgressState.DEFAULT,
    dialogState: LoginDialogStates = LoginDialogStates.NOTHING
) {
    val colorBackground = if (isSystemInDarkTheme()) colorPrimaryDark else colorPrimary
    val focusManager = LocalFocusManager.current
    BackHandler(true) {
        actions.onBackClick.invoke()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorBackground)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.baseline_menu_book_24),
            contentDescription = "Logo"
        )
        Spacer(modifier = Modifier.height(30.dp))

        CustomTextField(
            value = data.login,
            hiltTextId = R.string.login_hint,
            onValueChange = { actions.onLoginChanged.invoke(it) },
            imageAction = ImeAction.Next,
            keyboardType = KeyboardType.Email,
            focusManager = focusManager
        )

        Spacer(modifier = Modifier.height(5.dp))

        CustomTextField(
            value = data.password,
            hiltTextId = R.string.password_hint,
            onValueChange = { actions.onPasswordChanged.invoke(it) },
            imageAction = ImeAction.Done,
            keyboardType = KeyboardType.Password,
            focusManager = focusManager
        )

        Spacer(modifier = Modifier.height(20.dp))
        if (progressState == LoginProgressState.PROGRESS) {
            CircularProgressIndicator(modifier = Modifier.size(40.dp))
        } else {
            Button(
                onClick = { actions.buttonLoginClicked.invoke() },
                modifier = Modifier.width(250.dp),
                colors = ButtonDefaults.buttonColors(colorButtonDefault),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(text = stringResource(id = R.string.login))
            }
        }
    }

    ShowEvents(actions.showRepositories, actions.evenFinished, dialogState)
}

@Composable
fun ShowEvents(
    showRepositoriesEvent: () -> Unit,
    evenFinished: () -> Unit,
    events: LoginDialogStates = LoginDialogStates.NOTHING
) {
    when (events) {
        LoginDialogStates.IS_INTERNET_ERROR -> OkDialog(ua.good.utils.R.string.error_internet, evenFinished::invoke)
        LoginDialogStates.IS_ERROR_LOGIN_DATA -> OkDialog(R.string.error_login_data, evenFinished::invoke)
        LoginDialogStates.IS_EMPTY_DATA_ERROR -> OkDialog(R.string.error_empty_data, evenFinished::invoke)
        LoginDialogStates.IS_UNKNOWN_ERROR -> OkDialog(ua.good.utils.R.string.error_unknown, evenFinished::invoke)
        LoginDialogStates.SUCCESS -> {
            evenFinished.invoke()
            showRepositoriesEvent.invoke()
        }
        else -> {
            // Реализация не нужна
        }
    }
}

@Preview
@Composable
fun PreviewDefault() {
    LoginScreen(progressState = LoginProgressState.DEFAULT)
}

@Preview
@Composable
fun UnknownError() {
    LoginScreen(
        progressState = LoginProgressState.DEFAULT,
        data = LoginAndPassword("1", "1"),
        dialogState = LoginDialogStates.IS_UNKNOWN_ERROR
    )
}
