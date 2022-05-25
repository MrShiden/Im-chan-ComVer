package components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun SimpleIconButton(modifier: Modifier = Modifier, icon: ImageVector, onClick: () -> Unit) {

    Card(
        modifier = modifier.clickable { onClick.invoke() },
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(0.dp, color = Color.Transparent)
    ) {
        Icon(imageVector = icon, contentDescription = null)


    }
}