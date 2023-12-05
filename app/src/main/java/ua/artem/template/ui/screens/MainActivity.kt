package ua.artem.template.ui.screens

import android.os.Bundle
import android.view.View
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ua.artem.template.base.BaseActivity
import ua.good.login.LoginScreen
import ua.good.repositories.RepositoriesScreen

/**
 * Главный экран приложения
 */
@AndroidEntryPoint
class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = Routes.LOGIN) {
                composable(Routes.LOGIN) {
                    LoginScreen(navController)
                }
                composable(Routes.REPOSITORIES) {
                    RepositoriesScreen(navController)
                }
            }
        }
    }
}
