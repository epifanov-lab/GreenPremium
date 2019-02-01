package com.lab.greenpremium.utills

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import android.view.View
import android.view.ViewGroup
import com.lab.greenpremium.PHOTO_FOR_JSON_COMPRESSION_QUALITY
import com.lab.greenpremium.data.network.ApiError
import org.apache.http.entity.mime.HttpMultipartMode
import org.apache.http.entity.mime.MultipartEntity
import org.apache.http.entity.mime.content.ByteArrayBody
import java.io.ByteArrayOutputStream
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt


operator fun ViewGroup.get(pos: Int): View = getChildAt(pos)

val ViewGroup.views: List<View>
    get() = (0 until childCount).map { getChildAt(it) }

/**
 * Converts double value to locale independent currency string. Always uses dots as decimal
 * separators and spaces as grouping separators.
 *
 * @param amount       value to be converted
 * @param groupingUsed true if grouping separation should be used for large number
 * (e.g. 1 000 000 instead of 1000000)
 * @param decimals     number of digits after decimal separator
 * @param currencySymbol currency symbol char
 * @return locale specific currency string
 */
fun currencyFormat(amount: Double?, groupingUsed: Boolean = true, decimals: Int = 2, currencySymbol: Char = '₽'): String? {
    if (amount == null) return null

    val formatSymbols = DecimalFormatSymbols()
    formatSymbols.decimalSeparator = '.'
    formatSymbols.groupingSeparator = ' '
    val formatter = DecimalFormat()
    formatter.decimalFormatSymbols = formatSymbols

    var mutableDecimals = decimals
    amount?.let { if (amount.roundToInt() % 1 == 0) mutableDecimals = 0 }
    formatter.minimumFractionDigits = mutableDecimals

    formatter.isGroupingUsed = groupingUsed
    return "${formatter.format(amount)} $currencySymbol"
}

fun getErrorMessage(throwable: Throwable): String? {
    return (throwable as? ApiError)?.title ?: throwable.message
}

fun getTimestampFromDateString(dateString: String?, dateFormat: SimpleDateFormat = SimpleDateFormat("dd-MM-yyyy hh:mm:ss", Locale.getDefault())): Long? {

    if (dateString == null) return null

    var result: Long = 0
    try {
        result = dateFormat.parse(dateString).time
    } catch (e: ParseException) {
        e.printStackTrace()
    }

    return result
}

fun getFormattedDateString(timestamp: Long): String {
    return SimpleDateFormat("dd.MM.yyyy kk:mm:ss", Locale.getDefault()).format(timestamp)
}

fun getTimeFromTimestamp(timestamp: Long): String {
    val sdf = SimpleDateFormat("kk:mm", Locale.getDefault())
    return sdf.format(timestamp)
}

fun geDayFromTimestamp(timestamp: Long): String {
    val sdf = SimpleDateFormat("dd", Locale.getDefault())
    return sdf.format(timestamp)
}

fun getMonthStringFromTimestamp(timestamp: Long): String {
    val ruMonthNames = arrayOf("Января", "Февраля", "Марта", "Апреля", "Мая", "Июня",
            "Июля", "Августа", "Сентября", "Октября", "Ноября", "Декабря")
    val calendar = Calendar.getInstance()
    calendar.time = Date(timestamp)
    return ruMonthNames[calendar.get(Calendar.MONTH)]
}

fun getEncodedStringFromUri(context: Context, uri: Uri): String {
    val imageStream = context.contentResolver.openInputStream(uri)
    val selectedImage = BitmapFactory.decodeStream(imageStream)
    val encodedImage: String
    val byteArrayBitmapStream = ByteArrayOutputStream()
    selectedImage.compress(Bitmap.CompressFormat.PNG, PHOTO_FOR_JSON_COMPRESSION_QUALITY,
            byteArrayBitmapStream)
    val b = byteArrayBitmapStream.toByteArray()
    encodedImage = Base64.encodeToString(b, Base64.DEFAULT)
    return encodedImage
}

fun getEncodedStringFromUri2(uri: Uri): String {
    val bm = BitmapFactory.decodeFile(uri.path)
    val baos = ByteArrayOutputStream()
    bm.compress(Bitmap.CompressFormat.JPEG, 100, baos) //bm is the bitmap object
    val b = baos.toByteArray()
    return Base64.encodeToString(b, Base64.DEFAULT)
}

fun getMultipartEntityFromPhotoUri(uri: Uri, index: Int): String {
    val bm = BitmapFactory.decodeFile(uri.path)
    val baos = ByteArrayOutputStream()
    bm.compress(Bitmap.CompressFormat.JPEG, 50, baos)
    val data = baos.toByteArray()

    val bab = ByteArrayBody(data, uri.lastPathSegment)
    val entity = MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE)
    entity.addPart("photos[$index]", bab)
    return entity.toString()
}