package utils

import Models.Imagenes
import androidx.compose.ui.res.loadImageBitmap
import kotlinx.coroutines.flow.Flow
import java.io.File



class MainFunctions {

 fun convert(wallpaper: Boolean,name:Boolean,imageList: List<Imagenes>,convertPath: String){
     if (wallpaper){
         getWallpaper(imageList,convertPath)
     }
     if (name){

     }

 }
    fun getWallpaper(imageList:List<Imagenes>,convertPath:String){

        imageList.sortedBy {
            it.nombre
        }
        imageList.forEach{
            if (!it.extension.isNullOrBlank()) {
                val newPath = File(convertPath + "/" + it.nombre)
                it.file!!.renameTo(newPath)
            }
        }



    }

    fun getImagesList(imagesPath: String,wallpaper:Boolean):List<Imagenes>{


        val ubicacion = File(imagesPath)
        var id = 0

       val newImagenesList = mutableListOf<Imagenes>()

            if (wallpaper) {
                ubicacion.walk(FileWalkDirection.TOP_DOWN).forEach {
                    if (!it.extension.isNullOrBlank() && getImageWidth(it) > getImageHeight(it)) {
                        val imagen = Imagenes(
                            id = id++,
                            nombre = it.name,
                            imagesPath,
                            height = getImageHeight(it),
                            width = getImageWidth(it),
                            extension = it.extension,
                            file = it
                        )
                        newImagenesList.add(imagen)
                    }

                }

            } else {

                ubicacion.walk(FileWalkDirection.TOP_DOWN).forEach {
                    if (!it.extension.isNullOrBlank()) {
                        val imagen = Imagenes(
                            id = id++,
                            nombre = it.name,
                            imagesPath,
                            height = getImageHeight(it),
                            width = getImageWidth(it),
                            extension = it.extension,
                            file = it
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