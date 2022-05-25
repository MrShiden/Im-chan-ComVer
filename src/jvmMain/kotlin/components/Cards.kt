package components

import Models.Imagenes
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.useResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.skia.Bitmap
import java.io.File
import java.io.IOException

@Composable
fun SimpleTopCardInfo(imagen: Imagenes, width:Dp, height:Float, onClick:()->Unit){
    Card(modifier = Modifier.width(width).fillMaxHeight(height).padding(horizontal = 4.dp, vertical = 8.dp), shape = RoundedCornerShape(12.dp)) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(imagen.nombre)
            Text(imagen.width.toString())
            Text(imagen.height.toString())
        }

    }

}

@Composable
fun SimpleImageCardOptions(modifier: Modifier = Modifier,opt1Text:String,option2Text:String,option1Click:() -> Unit,option2Click:()-> Unit,opt1Checked:Boolean,opt2Checked:Boolean){
    Card(modifier = modifier.height(400.dp).width(200.dp)) {
        Column(modifier.fillMaxSize()) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(text = "Process:", style = MaterialTheme.typography.h6)
            }
            Row(modifier = Modifier.fillMaxWidth().padding(top = 8.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text(text = opt1Text, style = MaterialTheme.typography.subtitle1, fontStyle = FontStyle.Italic)
                IconToggleButton(checked = opt1Checked,
                onCheckedChange = {option1Click.invoke()}){
                    val transition = updateTransition(opt1Checked, label = "Checked indicator")

                    val tint by transition.animateColor(
                        label = "Tint"
                    ) { isChecked ->
                        if (isChecked) Color.Green else Color.LightGray
                    }

                    val size by transition.animateDp(
                        transitionSpec = {
                            if (false isTransitioningTo true) {
                                keyframes {
                                    durationMillis = 250
                                    30.dp at 0 with LinearOutSlowInEasing // for 0-15 ms
                                    35.dp at 15 with FastOutLinearInEasing // for 15-75 ms
                                    40.dp at 75 // ms
                                    35.dp at 150 // ms
                                }
                            } else {
                                spring(stiffness = Spring.StiffnessVeryLow)
                            }
                        },
                        label = "Size"
                    ) { 30.dp }

                    Icon(
                        imageVector = if (opt1Checked) Icons.Outlined.Check else Icons.Outlined.Check,
                        contentDescription = null,
                        tint = tint,
                        modifier = Modifier.size(size)
                    )
                }

                }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text(text = option2Text, style = MaterialTheme.typography.subtitle1, fontStyle = FontStyle.Italic)
                IconToggleButton(checked = opt1Checked,
                    onCheckedChange = {option2Click.invoke()}){
                    val transition = updateTransition(opt2Checked, label = "Checked indicator")

                    val tint by transition.animateColor(
                        label = "Tint"
                    ) { isChecked ->
                        if (isChecked) Color.Green else Color.LightGray
                    }

                    val size by transition.animateDp(
                        transitionSpec = {
                            if (false isTransitioningTo true) {
                                keyframes {
                                    durationMillis = 250
                                    30.dp at 0 with LinearOutSlowInEasing // for 0-15 ms
                                    35.dp at 15 with FastOutLinearInEasing // for 15-75 ms
                                    40.dp at 75 // ms
                                    35.dp at 150 // ms
                                }
                            } else {
                                spring(stiffness = Spring.StiffnessVeryLow)
                            }
                        },
                        label = "Size"
                    ) { 30.dp }

                    Icon(
                        imageVector = if (opt2Checked) Icons.Outlined.Check else Icons.Outlined.Check,
                        contentDescription = null,
                        tint = tint,
                        modifier = Modifier.size(size)
                    )
                }

            }

            }
        }

    }


@Composable
fun SimpleImageCard(imagen: Imagenes){

    Card(modifier = Modifier.width(150.dp).height(150.dp).padding(2.dp)) {
        Column() {

           val imageBitmap = remember {
               loadImageBitmap(imagen.file!!.inputStream())
           }



           /*Image(
               painter = BitmapPainter(image = imageBitmap),
                contentDescription = "${imagen.nombre} loaded from ${imagen.path}",
                modifier = Modifier.fillMaxWidth(),
               contentScale = ContentScale.Crop
            )*/

            AsyncImage(load = { components.loadImageBitmap(File("${imagen.path}/${imagen.nombre}"))}, painterFor = { remember { BitmapPainter(imageBitmap) }},
            contentDescription = "Bla", modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)



        }
    }
}


@Composable
fun <T> AsyncImage(
    load: suspend () -> T,
    painterFor: @Composable (T) -> Painter,
    contentDescription: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit,
) {
    val image: T? by produceState<T?>(null) {
        value = withContext(Dispatchers.IO) {
            try {
                load()
            } catch (e: IOException) {
                // instead of printing to console, you can also write this to log,
                // or show some error placeholder
                e.printStackTrace()
                null
            }
        }
    }

    if (image != null) {
        Image(
            painter = painterFor(image!!),
            contentDescription = contentDescription,
            contentScale = contentScale,
            modifier = modifier
        )
    }
}

/* Loading from file with java.io API */

fun loadImageBitmap(file: File): ImageBitmap =
    file.inputStream().buffered().use(::loadImageBitmap)


