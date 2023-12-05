package ua.good.login

import android.app.Activity
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import ua.good.utils.base.compose.colorButtonDefault
import ua.good.utils.base.compose.colorPrimary
import ua.good.utils.base.compose.colorPrimaryDark
import ua.good.utils.base.compose.widget.OkDialog

/**
 * Экран авторизации.
 */
@Composable
fun LoginScreen(navigation: NavHostController) {
    val model: LoginModel = hiltViewModel()
    val login by model.login.collectAsStateWithLifecycle()
    val password by model.password.collectAsStateWithLifecycle()
    val state by model.state.collectAsStateWithLifecycle()
    val colorBackground = if (isSystemInDarkTheme()) colorPrimaryDark else colorPrimary
    val focusManager = LocalFocusManager.current

    BackHandler(true) {
        (navigation.context as Activity).finish()
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
            value = login,
            hiltTextId = R.string.login_hint,
            onValueChange = { model.loginChanged(it) },
            imageAction = ImeAction.Next,
            keyboardType = KeyboardType.Email,
            focusManager = focusManager
        )

        Spacer(modifier = Modifier.height(5.dp))

        CustomTextField(
            value = password,
            hiltTextId = R.string.password_hint,
            onValueChange = { model.passwordChanged(it) },
            imageAction = ImeAction.Done,
            keyboardType = KeyboardType.Password,
            focusManager = focusManager
        )

        Spacer(modifier = Modifier.height(20.dp))
        if (state == LoginProgressState.PROGRESS) {
            CircularProgressIndicator(modifier = Modifier.size(40.dp))
        } else {
            Button(
                onClick = { model.buttonLoginClicked() },
                modifier = Modifier.width(250.dp),
                colors = ButtonDefaults.buttonColors(colorButtonDefault),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(text = stringResource(id = R.string.login))
            }
        }
    }

    ShowEvents(navigation)
}

@Composable
fun CustomTextField(
    value: String,
    hiltTextId: Int,
    onValueChange: (String) -> Unit,
    imageAction: ImeAction,
    keyboardType: KeyboardType,
    focusManager: FocusManager
) {
    TextField(
        colors = TextFieldDefaults.colors(
            disabledTextColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        shape = RoundedCornerShape(8.dp),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = imageAction,
            keyboardType = keyboardType
        ),
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = stringResource(id = hiltTextId),
                color = Color.Gray
            )
        },
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
    )
}

@Composable
fun ShowEvents(navigation: NavHostController) {
    val model: LoginModel = hiltViewModel()
    val events by model.events.collectAsStateWithLifecycle()

    val evenFinished = { model.eventFinished() }
    when (events) {
        LoginEvents.IS_INTERNET_ERROR -> OkDialog(ua.good.utils.R.string.error_internet, evenFinished)
        LoginEvents.IS_ERROR_LOGIN_DATA -> OkDialog(R.string.error_login_data, evenFinished)
        LoginEvents.IS_EMPTY_DATA_ERROR -> OkDialog(R.string.error_empty_data, evenFinished)
        LoginEvents.IS_UNKNOWN_ERROR -> OkDialog(ua.good.utils.R.string.error_unknown, evenFinished)
        LoginEvents.SCREEN_HIDED -> {
            evenFinished.invoke()
            navigation.popBackStack()
        }
        LoginEvents.SUCCESS -> {
            evenFinished.invoke()
            navigation.navigate("repositories")
        }
        else -> {
            // Реализация не нужна
        }
    }
}

@Preview
@Composable
fun PreviewMessageCard() {
    // LoginScreen()
}
