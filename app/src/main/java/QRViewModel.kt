package com.example.proyectoappv3

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class QRViewModel : ViewModel() {
    val qrBitmap = MutableLiveData<Bitmap>()
}
