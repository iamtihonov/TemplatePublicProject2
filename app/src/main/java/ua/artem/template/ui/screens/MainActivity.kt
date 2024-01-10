package ua.artem.template.ui.screens

import android.os.Bundle
import android.view.View
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ua.artem.template.base.BaseActivity
import ua.good.login.LoginRoute
import ua.good.repositories.RepositoriesRoute

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
            val showRepositoriesEvent = {
                navController.navigate(Routes.REPOSITORIES)
                navController.clearBackStack(Routes.LOGIN)
                Unit
            }
            val hideApplicationEvent = { finish() }
            NavHost(navController = navController, startDestination = Routes.LOGIN) {
                composable(Routes.LOGIN) {
                    LoginRoute(showRepositoriesEvent, hideApplicationEvent)
                }
                composable(Routes.REPOSITORIES) {
                    RepositoriesRoute {
                        // Можно еще почитать:
                        // https://oguzhanaslann.medium.com/handling-back-presses-in-jetpack-compose-and-onbackinvokedcallback-982e805173f0
                        // Используя navigate увеличивается backstack
                        navController.popBackStack(Routes.LOGIN, false)
                    }
                }
            }
        }
    }
}
