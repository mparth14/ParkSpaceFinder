package com.parkspace.finder.viewmodel

import android.util.Log
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import com.parkspace.finder.data.BookingDetailRepository
import com.parkspace.finder.data.BookingDetails
import com.parkspace.finder.data.ParkingSpace
import com.parkspace.finder.data.ParkingSpaceRepository
import com.parkspace.finder.data.Resource
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * Generates a QR code bitmap based on the provided data.
 *
 * @param qrData The data to encode into the QR code.
 * @return The generated QR code bitmap.
 */
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

/**
 * ViewModel responsible for managing booking detail data.
 *
 * @property bookingId The ID of the booking.
 * @property bookingDetailRepository Repository for accessing booking detail data.
 * @property parkingSpaceRepository Repository for accessing parking space data.
 */
@HiltViewModel(assistedFactory = BookingDetailViewModel.Factory::class)
class BookingDetailViewModel @AssistedInject constructor(
    @Assisted private val bookingId: String,
    private val bookingDetailRepository: BookingDetailRepository,
    private val parkingSpaceRepository: ParkingSpaceRepository
) : ViewModel(){

    @AssistedFactory
    interface Factory {
        fun create(bookingId: String): BookingDetailViewModel
    }

    private val _bookingDetail = MutableStateFlow<Resource<BookingDetails>?>(null)
    val bookingDetail: StateFlow<Resource<BookingDetails>?> = _bookingDetail

    private val _bookedParkingSpace = MutableStateFlow<Resource<ParkingSpace?>?>(null)
    val bookedParkingSpace: StateFlow<Resource<ParkingSpace?>?> = _bookedParkingSpace

    private val _qrCodeBitmap = MutableStateFlow<ImageBitmap?>(null)
    val qrCodeBitmap: StateFlow<ImageBitmap?> = _qrCodeBitmap

    init {
        viewModelScope.launch {
            _bookingDetail.value = Resource.Loading
            _bookingDetail.value = bookingDetailRepository.getBookingDetail(bookingId)
            if(_bookingDetail.value is Resource.Success){
                val details = (_bookingDetail.value as Resource.Success).result
                viewModelScope.launch {
                    _bookedParkingSpace.value = Resource.Loading
                    Log.d("BookingDetailViewModel", "Booking id: fetching parking with lot ${details.lotId}")
                    _bookedParkingSpace.value =  parkingSpaceRepository.getParkingSpaceById(details.lotId)
                }
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

    fun openParkingSpaceInMaps(){
        if(_bookedParkingSpace.value is Resource.Success){

        }
    }
}