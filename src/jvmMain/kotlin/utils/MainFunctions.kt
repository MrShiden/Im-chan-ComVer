package utils

import Models.Imagenes
import java.awt.image.BufferedImage

import java.io.File
import java.net.URL
import javax.imageio.ImageIO


class MainFunctions {

    fun getPath(imagesPath:String,pathToConvert:String){


    }

    fun getImagesList(imagesPath: String):List<Imagenes>{
        val ubicacion = File(imagesPath)
        var id = 0

        val newImagenesList = mutableListOf<Imagenes>()

        ubicacion.walk(FileWalkDirection.TOP_DOWN).forEach {
            if (!it.extension.isNullOrBlank()){
                val imagen = Imagenes(id = id++,
                    nombre = it.name,imagesPath, height = 50, width = 50,file = it)
                newImagenesList.add(imagen)
            }

        }

        return newImagenesList

    }

    fun getImageHeight(file: File):Int{

        val image = ImageIO.read(file)
        return image.height
    }
    fun getImageWidth(file: File):Int{

        val image = ImageIO.read(file)
        return image.width
    }
}