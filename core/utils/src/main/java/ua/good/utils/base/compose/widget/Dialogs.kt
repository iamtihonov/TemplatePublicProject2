package ua.good.utils.base.compose.widget

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import ua.good.utils.R

@Composable
fun SingleSelectDialog(
    titleId: Int,
    optionsList: Array<String>,
    defaultPosition: Int,
    buttonTextId: Int = R.string.select,
    onSubmitButtonClick: (Int) -> Unit,
    onDismissRequest: () -> Unit
) {
    var selectedOption by rememberSaveable { mutableIntStateOf(defaultPosition) }

    Dialog(onDismissRequest = { onDismissRequest.invoke() }) {
        Surface(
            modifier = Modifier.width(300.dp),
            shape = RoundedCornerShape(10.dp)
        ) {
            Column(
                modifier = Modifier.padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = stringResource(id = titleId), fontSize = 20.sp)
                Spacer(modifier = Modifier.height(10.dp))

                optionsList.forEachIndexed { index, s ->
                    AppRadioButton(s, optionsList[selectedOption]) {
                        selectedOption = index
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = {
                        onSubmitButtonClick.invoke(selectedOption)
                        onDismissRequest.invoke()
                    },
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text(text = stringResource(id = buttonTextId))
                }
            }
        }
    }
}

@Composable
fun AppRadioButton(text: String, selectedValue: String, onClickListener: (String) -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .selectable(
                selected = (text == selectedValue),
                onClick = {
                    onClickListener(text)
                }
            )
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = (text == selectedValue),
            onClick = {
                onClickListener(text)
            }
        )
        Text(
            text = text,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}
