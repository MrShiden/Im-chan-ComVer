// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import Models.imagenesListTest
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import components.*
import theme.SimpleTheme

@Composable
@Preview
fun App() {

    SimpleTheme(colorSelected = 2) {
        Scaffold(topBar = {
            Row(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                SimpleIconButton(modifier = Modifier.padding(end = 8.dp), icon = Icons.Default.Menu, onClick = {

                })
                Text(text = "Im-Chan")

            }
        }) {

            MainScreen()

        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen() {
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {

        val prefijo = remember {
            mutableStateOf("")
        }
        val originPath = remember {
            mutableStateOf("")
        }
        val convertPath = remember {
            mutableStateOf("")
        }

        val opt1Checked = remember {
            mutableStateOf(true)
        }
        val opt2Checked = remember {
            mutableStateOf(false)
        }


        LazyRow(modifier = Modifier.fillMaxWidth()) {
            items(imagenesListTest()) {
                SimpleTopCardInfo(imagen = it, height = 0.2f, width = 150.dp, onClick = {

                })
            }
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
            SimpleInputField(valueState = prefijo, labelId = "Prefijo", modifier = Modifier.width(200.dp))
            SimpleInputField(valueState = originPath, labelId = "Path to convert", modifier = Modifier.width(200.dp))
            SimpleInputField(valueState = convertPath, labelId = "Destiny Path", modifier = Modifier.width(200.dp))
        }

        Row(modifier = Modifier.fillMaxWidth().padding(top = 24.dp), horizontalArrangement = Arrangement.SpaceBetween) {

            SimpleImageCardOptions(
                modifier = Modifier.padding(start = 24.dp, end = 24.dp, bottom = 24.dp),
                opt1Text = "Change Name:",
                opt1Checked = opt1Checked.value,
                opt2Checked = opt2Checked.value,
                option1Click = {
                    opt1Checked.value = !opt1Checked.value },
                option2Click = { opt2Checked.value = !opt2Checked.value },
                option2Text = "Separate Wall:"
            )

            Card(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {

                LazyVerticalGrid(cells = GridCells.Adaptive(minSize = 128.dp)){
                    items(imagenesListTest()) {
                        SimpleTopCardInfo(imagen = it, height = 0.2f, width = 150.dp, onClick = {

                        })
                    }

                }

            }




        }
    }

}


fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
