package com.vaibhavrawat.medialoader

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.AsyncTask
import android.widget.ImageView
import android.widget.VideoView
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

object MediaLoader {
    fun loadImage(imageView: ImageView, uri: Uri) {
        val context = imageView.context
        imageView.setImageURI(uri)
    }

    fun loadImage(imageView: ImageView, url: String) {
        ImageLoaderTask(imageView).execute(url)
    }

    fun loadVideo(videoView: VideoView, uri: Uri) {
        videoView.setVideoURI(uri)
        videoView.requestFocus()
        videoView.start()
    }

    fun loadVideo(videoView: VideoView, url: String) {
        videoView.setVideoPath(url)
        videoView.requestFocus()
        videoView.start()
    }

    private class ImageLoaderTask(val imageView: ImageView) : AsyncTask<String, Void, Bitmap?>() {
        override fun doInBackground(vararg params: String): Bitmap? {
            val urlString = params[0]
            var bitmap: Bitmap? = null
            try {
                val url = URL(urlString)
                val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
                connection.doInput = true
                connection.connect()
                val input: InputStream = connection.inputStream
                bitmap = BitmapFactory.decodeStream(input)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return bitmap
        }

        override fun onPostExecute(result: Bitmap?) {
            result?.let {
                imageView.setImageBitmap(it)
            }
        }
    }
}
