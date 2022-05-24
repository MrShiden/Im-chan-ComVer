package components

import Models.Imagenes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

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