// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import Models.Imagenes
import Models.imagenesListTest
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import components.*
import kotlinx.coroutines.*
import theme.SimpleTheme
import utils.MainFunctions
import javax.swing.JFileChooser
import javax.swing.filechooser.FileNameExtensionFilter

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

        val scope = rememberCoroutineScope()


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

        var imageList: List<Imagenes> by remember {
            mutableStateOf(emptyList())
        }


        val loading = remember {
            mutableStateOf(false)
        }


        val fc = JFileChooser()
        val filter = FileNameExtensionFilter("Imagenes", "jpg", "png", "gif", "bmp", "jpeg")
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY)
        fc.isAcceptAllFileFilterUsed = false
        fc.addChoosableFileFilter(filter)


        Row(modifier = Modifier.fillMaxWidth()) {

            SimpleTopCardInfo(
                imageList.size.toString(),
                onClick = {},
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
            )
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
            SimpleInputField(
                valueState = prefijo,
                labelId = "Prefijo",
                modifier = Modifier.width(200.dp),
                onClick = {},
                enabled = opt1Checked.value
            )
            SimpleInputField(
                valueState = originPath,
                labelId = "Path to convert",
                modifier = Modifier.width(200.dp),
                enabled = false,
                onClick = {

                    val returnVal = fc.showOpenDialog(null)
                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        val path = fc.selectedFile.path
                        originPath.value = path
                    }


                })
            SimpleInputField(
                valueState = convertPath,
                labelId = "Destiny Path",
                modifier = Modifier.width(200.dp),
                enabled = false,
                onClick = {

                    val returnVal = fc.showOpenDialog(null)
                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        val path = fc.selectedFile.path
                        convertPath.value = path
                    }

                })
        }

        Row(modifier = Modifier.fillMaxWidth().padding(top = 24.dp), horizontalArrangement = Arrangement.SpaceBetween) {

            SimpleImageCardOptions(
                modifier = Modifier.padding(start = 24.dp, end = 24.dp, bottom = 24.dp),
                opt1Text = "Change Name:",
                opt1Checked = opt1Checked.value,
                opt2Checked = opt2Checked.value,
                option1Click = {
                    opt1Checked.value = !opt1Checked.value
                    prefijo.value = ""
                },
                option2Click = { opt2Checked.value = !opt2Checked.value },
                option2Text = "Separate Wall:",
                start = {
                    scope.launch {
                        MainFunctions().convert(
                            wallpaper = opt2Checked.value,
                            name = opt1Checked.value,
                            imageList,
                            convertPath.value
                        )
                        opt2Checked.value = false
                        imageList = emptyList()
                        loading.value = true
                        imageList = MainFunctions().getImagesList(originPath.value, wallpaper = opt2Checked.value)
                        loading.value = false
                    }

                }, load = {
                    scope.launch(Dispatchers.IO) {
                        imageList = emptyList()
                        loading.value = true
                        imageList = MainFunctions().getImagesList(originPath.value, wallpaper = opt2Checked.value)
                        loading.value = false

                    }


                }
            )

            Card(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
                if (loading.value) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        SimpleCircularProgressLoading(isDisplayed = loading.value)
                    }
                }


                LazyVerticalGrid(cells = GridCells.Adaptive(minSize = 128.dp)) {

                    items(imageList) {

                        SimpleImageCard(it)


                    }


                }


            }


        }
    }

}


fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Im-Chan",
        state = WindowState(position = WindowPosition.Aligned(Alignment.Center))
    ) {
        App()
    }
}
