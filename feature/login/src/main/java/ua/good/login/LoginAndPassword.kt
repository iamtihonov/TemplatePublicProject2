package ua.good.login

data class LoginAndPassword(val login: String = "", val password: String = "")

data class LoginActions(
    val showRepositories: () -> Unit = {},
    val evenFinished: () -> Unit = {},
    val onBackClick: () -> Unit = {},
    val onLoginChanged: (String) -> Unit = {},
    val onPasswordChanged: (String) -> Unit = {},
    val buttonLoginClicked: () -> Unit = {}
)
