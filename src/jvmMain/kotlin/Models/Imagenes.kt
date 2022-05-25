package Models

import java.io.File

data class Imagenes(
    val id:Int,
    val nombre:String,
    val path:String,
    val height:Int,
    val width:Int,
    val extension:String,
    val file:File?
)

fun imagenesListTest():List<Imagenes>{
    return listOf(

        Imagenes(
            id = 1,
            nombre = "21eb9ade2f15021a9b35274360635b16.jpeg",
            path = "~/Imágenes/Capcom/ChunLi",
            height = 1920,
            width = 120,
            extension = "vla",
            file = null
        ),
        Imagenes(
            id = 1,
            nombre = "21eb9ade2f15021a9b35274360635b16.jpeg",
            path = "~/Imágenes/Capcom/ChunLi",
            height = 1920,
            width = 120,
            extension = "vla",
            file = null
        ),
        Imagenes(
            id = 1,
            nombre = "21eb9ade2f15021a9b35274360635b16.jpeg",
            path = "~/Imágenes/Capcom/ChunLi",
            height = 1920,
            width = 120,
            extension = "vla",
            file = null
        ),
        Imagenes(
            id = 1,
            nombre = "21eb9ade2f15021a9b35274360635b16.jpeg",
            path = "~/Imágenes/Capcom/ChunLi",
            height = 1920,
            width = 120,
            extension = "vla",
            file = null
        ),
        Imagenes(
            id = 1,
            nombre = "21eb9ade2f15021a9b35274360635b16.jpeg",
            path = "~/Imágenes/Capcom/ChunLi",
            height = 1920,
            width = 120,
            extension = "vla",
            file = null
        ),
        Imagenes(
            id = 1,
            nombre = "21eb9ade2f15021a9b35274360635b16.jpeg",
            path = "~/Imágenes/Capcom/ChunLi",
            height = 1920,
            width = 120,
            extension = "vla",
            file = null
        ),


    )
}
