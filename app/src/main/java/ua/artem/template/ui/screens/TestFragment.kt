package ua.artem.template.ui.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment

/**
 * Удалить
 */
class TestFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return ComposeView(requireContext()).apply {
            setContent {
                InitScreen()
            }
        }
    }

    @Composable
    fun InitScreen() {
        HelloContent()
    }

    /**
     * Так данные не изменяться
     *
     * Если в качестве параметров передавать изменяемые объекты такие, как ArrayList.
     * Могут отображаться или устаревшие данные, или неверные
     */
    @Composable
    private fun HelloContent() {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Hello!",
                modifier = Modifier.padding(bottom = 8.dp),
                style = MaterialTheme.typography.bodyMedium
            )
            OutlinedTextField(
                value = "",
                onValueChange = { },
                label = { Text("Name") }
            )
        }
    }
}
