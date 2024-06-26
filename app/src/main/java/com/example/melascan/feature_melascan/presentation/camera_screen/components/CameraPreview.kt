package com.example.melascan.feature_melascan.presentation.camera_screen.components

import android.annotation.SuppressLint
import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import androidx.camera.core.CameraSelector
import androidx.camera.core.FocusMeteringAction
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.core.view.GestureDetectorCompat
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@SuppressLint("RestrictedApi", "ClickableViewAccessibility")
@Composable
fun CameraPreviewScreen(
    captureImage: suspend (ImageCapture, Context) -> Unit
) {
    val lensFacing = CameraSelector.LENS_FACING_BACK
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val preview = Preview.Builder().build()
    val previewView = remember {
        PreviewView(context)
    }

    val cameraxSelector = CameraSelector.Builder().requireLensFacing(lensFacing).build()
    val imageCapture = remember {
        ImageCapture.Builder().build()
    }
    val coroutineScope = rememberCoroutineScope()

    val col = MaterialTheme.colorScheme.onPrimary

    val cameraController = remember {
        LifecycleCameraController(context).apply {
            bindToLifecycle(lifecycleOwner)
        }
    }

    LaunchedEffect(lensFacing) {
        val cameraProvider = context.getCameraProvider()
        cameraProvider.unbindAll()
        cameraProvider.bindToLifecycle(lifecycleOwner, cameraxSelector, preview, imageCapture)
        preview.setSurfaceProvider(previewView.surfaceProvider)

        val scaleGestureDetector = ScaleGestureDetector(context,
            object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
                override fun onScale(detector: ScaleGestureDetector): Boolean {
                    val camera = preview.camera ?: return false
                    val zoomState = camera.cameraInfo.zoomState
                    val currentZoomRatio: Float = zoomState.value?.zoomRatio ?: 1f
                    camera.cameraControl.setZoomRatio(
                        detector.scaleFactor * currentZoomRatio
                    )
                    return true
                }
            }
        )

        // Set the scaleGestureDetector in a touch listener on the PreviewView.
        previewView.setOnTouchListener { _, event ->
            var didConsume = scaleGestureDetector.onTouchEvent(event)
            if (!scaleGestureDetector.isInProgress) {
                // See pinch-to-zooom scenario for gestureDetector definition.
                didConsume = scaleGestureDetector.onTouchEvent(event)
            }
            didConsume
        }
    }

    Box(contentAlignment = Alignment.BottomCenter, modifier = Modifier.fillMaxSize()) {
        AndroidView({ previewView }, modifier = Modifier.fillMaxSize())

        IconButton(
            onClick = {
                coroutineScope.launch {
                    captureImage(imageCapture, context)
                }
            },
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            Icon(
                imageVector = Icons.Default.PhotoCamera,
                contentDescription = "Take a picture",
                tint = col,
                modifier = Modifier.scale(1.5f)
            )
        }
    }
}

/*
* val lensFacing = CameraSelector.LENS_FACING_BACK
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val preview = Preview.Builder().build()
    val previewView = remember {
        PreviewView(context)
    }

    val cameraxSelector = CameraSelector.Builder().requireLensFacing(lensFacing).build()
    val imageCapture = remember {
        ImageCapture.Builder().build()
    }
    val coroutineScope = rememberCoroutineScope()

    val col = MaterialTheme.colorScheme.onPrimary

    val cameraController = remember {
        LifecycleCameraController(context).apply {
            bindToLifecycle(lifecycleOwner)
        }
    }

    LaunchedEffect(lensFacing) {
        val cameraProvider = context.getCameraProvider()
        cameraProvider.unbindAll()
        cameraProvider.bindToLifecycle(lifecycleOwner, cameraxSelector, preview, imageCapture)
        preview.setSurfaceProvider(previewView.surfaceProvider)
    }

    // Handle tap-to-focus
    previewView.setOnTouchListener(View.OnTouchListener { _, motionEvent ->
        when (motionEvent.action) {
            MotionEvent.ACTION_UP -> {
                val factory = previewView.meteringPointFactory
                val point = factory.createPoint(motionEvent.x, motionEvent.y)
                val action = FocusMeteringAction.Builder(point).build()
                cameraController.cameraControl?.startFocusAndMetering(action)
                true
            }
            else -> false
        }
    })

    // Handle pinch-to-zoom
    val scaleGestureDetector = remember {
        ScaleGestureDetector(context, object : ScaleGestureDetector.OnScaleGestureListener {
            override fun onScale(detector: ScaleGestureDetector): Boolean {
                val currentZoomRatio = cameraController.cameraInfo?.zoomState?.value?.zoomRatio ?: 1f
                val delta = detector.scaleFactor
                cameraController.cameraControl?.setZoomRatio(currentZoomRatio * delta)
                return true
            }

            override fun onScaleBegin(detector: ScaleGestureDetector) = true
            override fun onScaleEnd(detector: ScaleGestureDetector) {}
        })
    }

    previewView.setOnTouchListener { _, motionEvent ->
        scaleGestureDetector.onTouchEvent(motionEvent)
        false
    }

    Box(contentAlignment = Alignment.BottomCenter, modifier = Modifier.fillMaxSize()) {
        AndroidView({ previewView }, modifier = Modifier.fillMaxSize())

        IconButton(
            onClick = {
                coroutineScope.launch {
                    captureImage(imageCapture, context)
                }
            },
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            Icon(
                imageVector = Icons.Default.PhotoCamera,
                contentDescription = "Take a picture",
                tint = col,
                modifier = Modifier.scale(1.5f)
            )
        }
    }
* */

private suspend fun Context.getCameraProvider(): ProcessCameraProvider =
    suspendCoroutine { continuation ->
        ProcessCameraProvider.getInstance(this).also { cameraProvider ->
            cameraProvider.addListener({
                continuation.resume(cameraProvider.get())
            }, ContextCompat.getMainExecutor(this))
        }
    }