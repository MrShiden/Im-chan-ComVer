package utils

import Models.Imagenes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.loadImageBitmap
import java.awt.image.BufferedImage

import java.io.File
import java.net.URL
import javax.imageio.ImageIO


class MainFunctions {

    fun getPath(imagesPath:String,pathToConvert:String){


    }

    fun getImagesList(imagesPath: String,wallpaper:Boolean):List<Imagenes>{
        val ubicacion = File(imagesPath)
        var id = 0

        val newImagenesList = mutableListOf<Imagenes>()

        if (wallpaper){

            ubicacion.walk(FileWalkDirection.TOP_DOWN).forEach {
                if (!it.extension.isNullOrBlank()&&getImageWidth(it)>getImageHeight(it)) {
                    val imagen = Imagenes(
                        id = id++,
                        nombre = it.name, imagesPath, height = getImageHeight(it), width = getImageWidth(it), file = it
                    )
                    newImagenesList.add(imagen)
                }

            }

        }else {

            ubicacion.walk(FileWalkDirection.TOP_DOWN).forEach {
                if (!it.extension.isNullOrBlank()) {
                    val imagen = Imagenes(
                        id = id++,
                        nombre = it.name, imagesPath, height = getImageHeight(it), width = getImageWidth(it), file = it
                    )
                    newImagenesList.add(imagen)
                }

            }
        }

        return newImagenesList

    }


    fun getImageHeight(file: File):Int{

        val imageBitmap = loadImageBitmap(file.inputStream())


        return imageBitmap.height
    }

    fun getImageWidth(file: File):Int{
        val imageBitmap = loadImageBitmap(file.inputStream())


        return imageBitmap.width
    }
}