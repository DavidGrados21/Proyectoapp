package com.example.proyectoappv3.SQLite.DB

import android.util.Log
import com.dropbox.core.DbxException
import com.dropbox.core.DbxRequestConfig
import com.dropbox.core.v2.DbxClientV2
import com.dropbox.core.v2.files.FileMetadata
import com.dropbox.core.v2.files.WriteMode
import java.io.File
import java.io.FileInputStream

class DropboxHelper {
    private lateinit var dropboxClient: DbxClientV2
    var urlImage: String? = null

    // Método para configurar el cliente de Dropbox con el access token
    fun initializeDropboxClient(accessToken: String) {
        val config = DbxRequestConfig.newBuilder("your-app-name").build()
        dropboxClient = DbxClientV2(config, accessToken)
    }

    // Método para subir un archivo a Dropbox
    fun uploadImageToDropbox(filePath: String) {
        val file = File(filePath)
        val fileInputStream = FileInputStream(file)
        val dropboxPath = "/path/to/upload/${file.name}"

        try {
            // Subir el archivo a Dropbox
            val metadata: FileMetadata = dropboxClient.files().uploadBuilder(dropboxPath)
                .withMode(WriteMode.ADD)
                .uploadAndFinish(fileInputStream)

            // Obtener el enlace compartido
            val sharedLink = dropboxClient.sharing().createSharedLinkWithSettings(dropboxPath)
            urlImage = sharedLink.url
            Log.d("Dropbox", "Imagen subida, URL: $urlImage")
        } catch (e: DbxException) {
            e.printStackTrace()
        }
    }
}