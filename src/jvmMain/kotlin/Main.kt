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
import androidx.compose.ui.unit.DpSize
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

val imageTesto = Imagenes(
            id = 0,
            nombre = "-",
            path = "-",
            height = 0,
            width = 0,
            extension = "-",
            file = null
        )


        val prefijo = remember { mutableStateOf("") }
        val originPath = remember { mutableStateOf("") }
        val convertPath = remember { mutableStateOf("") }
        val opt1Checked = remember { mutableStateOf(true) }
        val opt2Checked = remember { mutableStateOf(false) }
        var imageList: List<Imagenes> by remember { mutableStateOf(emptyList()) }
        val loading = remember { mutableStateOf(false) }
        val imagePreview = remember { mutableStateOf(imageTesto) }


        val fc = JFileChooser()
        val filter = FileNameExtensionFilter("Imagenes", "jpg", "png", "gif", "bmp", "jpeg")
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY)
        fc.isAcceptAllFileFilterUsed = false
        fc.addChoosableFileFilter(filter)


        Row(modifier = Modifier.fillMaxWidth().fillMaxHeight(0.15f), verticalAlignment = Alignment.CenterVertically) {

            SimpleTopCardInfo(
                imageList.size.toString(),
                onClick = {},
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
            )
            SimpleImCardInfo(imagePreview.value)
        }
        Row(modifier = Modifier.fillMaxWidth().fillMaxHeight(0.2f), horizontalArrangement = Arrangement.SpaceAround, verticalAlignment = Alignment.CenterVertically) {
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

            Column(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Center) {

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
                        scope.launch(Dispatchers.IO) {
                            MainFunctions().convert(
                                wallpaper = opt2Checked.value,
                                name = opt1Checked.value,
                                imageList,
                                convertPath.value,
                                prefijo.value
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
            }

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


                LazyVerticalGrid(cells = GridCells.Adaptive(minSize = 250.dp)) {

                    items(imageList) {

                        SimpleImageCard(it, onClick = {
                            imagePreview.value = it

                        })


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
        state = WindowState(position = WindowPosition.Aligned(Alignment.Center), size = DpSize(width = 1024.dp, height = 720.dp))
    ) {
        App()
    }
}
