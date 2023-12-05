package ua.good.utils.base.compose.widget

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun OkDialog(textId: Int, listener: () -> Unit) {
    AlertDialog(
        modifier = Modifier.padding(20.dp),
        onDismissRequest = { listener() },
        text = {
            Text(
                text = stringResource(id = textId),
                style = MaterialTheme.typography.bodyLarge
            )
        },
        confirmButton = {
            TextButton(
                onClick = listener,
                shape = MaterialTheme.shapes.medium
            ) {
                Text(text = "OK")
            }
        }
    )
}
