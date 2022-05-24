package Models

import java.io.File

data class Imagenes(
    val id:Int,
    val nombre:String,
    val height:Int,
    val width:Int,
    val file:File?
)

fun imagenesListTest():List<Imagenes>{
    return listOf(
        Imagenes(
            id = 1,
            nombre = "Dazu",
            height = 1920,
            width = 1080,
            file = null
        ),
        Imagenes(
            id = 1,
            nombre = "Dazu",
            height = 1920,
            width = 1080,
            file = null
        ),
        Imagenes(
            id = 1,
            nombre = "Dazu",
            height = 1920,
            width = 1080,
            file = null
        ),
    )
}
