package com.parkspace.finder.viewmodel

import android.graphics.Bitmap
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import com.parkspace.finder.data.BookingDetailRepository
import com.parkspace.finder.data.BookingDetails
import com.parkspace.finder.data.Resource
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


fun generateQRCodeBitmap(qrData: String): ImageBitmap {
    val writer = QRCodeWriter()
    val bitMatrix = writer.encode(qrData, BarcodeFormat.QR_CODE, 700, 700)
    val width = bitMatrix.width
    val height = bitMatrix.height
    val bmp = android.graphics.Bitmap.createBitmap(width, height, android.graphics.Bitmap.Config.RGB_565)
    for (x in 0 until width) {
        for (y in 0 until height) {
            bmp.setPixel(
                x,
                y,
                if (bitMatrix[x, y]) android.graphics.Color.BLACK else android.graphics.Color.WHITE
            )
        }
    }
    return bmp.asImageBitmap()
}

@HiltViewModel(assistedFactory = BookingDetailViewModel.Factory::class)
class BookingDetailViewModel @AssistedInject constructor(
    @Assisted private val bookingId: String,
    private val bookingDetailRepository: BookingDetailRepository
) : ViewModel(){

    @AssistedFactory
    interface Factory {
        fun create(bookingId: String): BookingDetailViewModel
    }

    private val _bookingDetail = MutableStateFlow<Resource<BookingDetails>?>(null)
    val bookingDetail: StateFlow<Resource<BookingDetails>?> = _bookingDetail

    private val _qrCodeBitmap = MutableStateFlow<ImageBitmap?>(null)
    val qrCodeBitmap: StateFlow<ImageBitmap?> = _qrCodeBitmap

    init {
        viewModelScope.launch {
            _bookingDetail.value = Resource.Loading
            _bookingDetail.value = bookingDetailRepository.getBookingDetail(bookingId)
            if(_bookingDetail.value is Resource.Success){
                val details = (_bookingDetail.value as Resource.Success).result
                val qrData = "Start Time: ${details.startTime}\n" +
                        "End Time: ${details.endTime}\n" +
                        "Spot Number: ${details.spotNumber}\n" +
                        "Price: ${details.price}\n" +
                        "Lot id: ${details.lotId}\n" +
                        "Booking id: ${details.id}\n"
                _qrCodeBitmap.value = generateQRCodeBitmap(qrData)
            }
        }
    }
}