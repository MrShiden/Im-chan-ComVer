package components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SimpleInputField(
    modifier: Modifier = Modifier,
    valueState: MutableState<String>,
    labelId: String,
    maxLine: Int = 5,
    isSingleLine: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
    textLenght: Int = 10,
    enabled: Boolean = true,
    onClick:() -> Unit


) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Column() {
        OutlinedTextField(
            value = valueState.value,
            onValueChange = { if (it.length <= textLenght) valueState.value = it },
            label = { Text(text = labelId) },
            singleLine = isSingleLine,
            textStyle = TextStyle(fontSize = 18.sp, color = MaterialTheme.colors.onBackground),
            modifier = modifier
                .fillMaxWidth().clickable { if (!enabled)onClick.invoke() },
            maxLines = maxLine,
            enabled = enabled,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                keyboardType = keyboardType,
                imeAction = imeAction
            ),
            keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() })
        )
        Text(
            text = "${valueState.value.length} / $textLenght",
            textAlign = TextAlign.End,
            color = MaterialTheme.colors.primary,
            style = MaterialTheme.typography.caption, //use the caption text style
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 5.dp)
        )
    }
}

//Simple TextField
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SimpleTextField(
    modifier: Modifier = Modifier,
    text: String,
    label: String,
    maxLine: Int = 1,
    singleLine:Boolean = false,
    onTextChange: (String) -> Unit,
    onImeAction: () -> Unit = {},
    imeiAction: ImeAction,
    keyboardType: KeyboardType = KeyboardType.Text

) {
    val keyBoardController = LocalSoftwareKeyboardController.current
    TextField(
        modifier = modifier,
        value = text, onValueChange = onTextChange,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent
        ),
        maxLines = maxLine,
        singleLine = singleLine,
        placeholder = { Text(text = label) },
//        label = { Text(text = label) },
        keyboardOptions = KeyboardOptions.Default.copy(
            capitalization = KeyboardCapitalization.Sentences,
            keyboardType = keyboardType,
            imeAction = imeiAction
        ),
        keyboardActions = KeyboardActions(onDone = {
            onImeAction()
            if (imeiAction == ImeAction.Done) keyBoardController?.hide() else keyBoardController?.show()


        }),

        )
}

//Simple DropDown
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SimpleDropDownMenu(
    modifier: Modifier = Modifier,
    valueState: MutableState<String>,
    list: List<String>,
    dropMenuDescription: String
) {
    var expanded by remember {
        mutableStateOf(false)
    }

    val listToCreate = listOf(list)
    val icon = if (expanded) {
        Icons.Filled.KeyboardArrowUp
    } else {
        Icons.Filled.KeyboardArrowDown
    }
    var textfieldSize by remember {
        mutableStateOf(Size.Zero)
    }

    val interactionSource = remember { MutableInteractionSource() }

    Column(modifier) {
        OutlinedTextField(
            value = valueState.value,
            onValueChange = { valueState.value = it },
            readOnly = true,
            enabled = false,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                disabledBorderColor = MaterialTheme.colors.primary,
//                textColor = MaterialTheme.colors.onSurface,
                disabledTextColor = MaterialTheme.colors.onSurface,
                disabledLabelColor = MaterialTheme.colors.primary
            ),
            modifier = modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }
                .onGloballyPositioned { coordinates -> textfieldSize = coordinates.size.toSize() },
            label = {
                Text(text = dropMenuDescription)
            },
            trailingIcon = {
                Surface(
                    color = Color.Transparent,
                    modifier = Modifier.size(40.dp),
                    shape = CircleShape,
                    onClick = {
                        expanded = !expanded
                    }) {
                    Column(
                        modifier = Modifier.fillMaxHeight(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
//                        modifier = Modifier.clickable { expanded = !expanded },
                            imageVector = icon,
                            tint = MaterialTheme.colors.onSurface,
                            contentDescription = "Icono $dropMenuDescription"
                        )
                    }


                }


            },

            )

        DropdownMenu(
            modifier = Modifier
                .width(with(LocalDensity.current) { textfieldSize.width.toDp() }),
            expanded = expanded,
            onDismissRequest = { expanded = false }) {
            list.forEach { label ->
                DropdownMenuItem(onClick = {
                    valueState.value = label
                    expanded = false

                }) {
                    Text(text = label)
                }

            }
        }


    }

}
