package com.spana.banksampahspana.util

import android.os.Environment
import android.text.Editable
import okhttp3.ResponseBody
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object Utils {
    fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

    fun saveFile(data: ResponseBody?): File {
        val filename = "data-${getCurrentTime()}.pdf"
        val storageDir =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val file = File(storageDir, filename)
        var inputStream: InputStream? = null
        var outputStream: OutputStream? = null

        try {
            inputStream = data?.byteStream()
            outputStream = FileOutputStream(file)

            val buffer = ByteArray(4096)
            var bytesRead: Int

            while (inputStream?.read(buffer).also { bytesRead = it ?: -1 } != -1) {
                outputStream.write(buffer, 0, bytesRead)
            }
            outputStream.flush()
        } finally {
            inputStream?.close()
            outputStream?.close()
        }

        return file
    }

    fun getCurrentTime(): String {
        val currentTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return currentTime.format(formatter)
    }
}