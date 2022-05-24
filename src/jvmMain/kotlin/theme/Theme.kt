package theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = TealPrimary,
    primaryVariant = TealDark,
    secondary = TealDark
)

private val LightColorPalette = lightColors(
    primary = TealPrimary,
    primaryVariant = TealDark,
    secondary = TealLight,

//     Other default colors to override
    background = BlackPrimary,
    surface = BlackLight,
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onBackground = TealPrimary,
    onSurface = TealLight,
    error = RedPrimary,
    onError = Color.Black
)

private val Testu = lightColors(
    primary = Color.Yellow,
    primaryVariant = Color.Red,
    secondary = Color.Green,

//     Other default colors to override
    background = BlackPrimary,
    surface = BlackLight,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = TealPrimary,
    onSurface = TealLight,
)

@Composable
fun SimpleTheme(colorSelected:Int,darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colorSelected = when(colorSelected){
        1 -> DarkColorPalette
        2 -> LightColorPalette
        3 -> Testu
        else -> DarkColorPalette

    }
    MaterialTheme(
        colors = if (darkTheme) DarkColorPalette else colorSelected,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}