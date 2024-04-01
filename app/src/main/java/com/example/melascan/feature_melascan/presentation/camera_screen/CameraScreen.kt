package com.example.melascan.feature_melascan.presentation.camera_screen

import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.compose.runtime.Composable
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.melascan.feature_melascan.presentation.camera_screen.components.CameraPreviewScreen
import com.example.melascan.feature_melascan.presentation.util.Screen
import java.util.UUID


@Composable
fun CameraScreen(
    navController: NavController,
) {
    CameraPreviewScreen { img, context ->
        val name = UUID.randomUUID().toString() + ".jpg"
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/MelaScan/")
        }
        val outputOptions = ImageCapture.OutputFileOptions
            .Builder(
                context.contentResolver,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            )
            .build()
        img.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(context),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    val path = outputFileResults.savedUri?.path
                    println("Success! path = $path")
                    navController.navigate("${Screen.DataInput.route}/${outputFileResults.savedUri.toString().replace('/', '^')}")
                }

                override fun onError(exception: ImageCaptureException) {
                    println("Failed $exception")
                }

        })
    }
}