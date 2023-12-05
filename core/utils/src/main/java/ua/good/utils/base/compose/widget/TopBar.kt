package ua.good.utils.base.compose.widget

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import ua.good.utils.base.compose.colorToolbar
import ua.good.utils.base.compose.colorToolbarDark

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(textId: Int, listener: () -> Unit, imageId: Int) {
    val toolbarColor = if (isSystemInDarkTheme()) colorToolbarDark else colorToolbar

    TopAppBar(
        title = {
            Text(
                text = stringResource(textId),
                color = Color.White
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = toolbarColor),
        actions = {
            IconButton(onClick = listener) {
                Icon(
                    painterResource(imageId),
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
    )
}
