package com.lab.greenpremium.utills

import android.view.View
import android.view.ViewGroup
import com.lab.greenpremium.data.network.ApiError
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

fun getTimestampFromDateString(dateString: String?, dateFormat: SimpleDateFormat = SimpleDateFormat("dd-mm-yyyy hh:mm:ss", Locale.getDefault())): Long? {

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
    val ruMonthNames = arrayOf("Январь", "Февраль", "Март", "Апрель", "Май", "Июнь",
            "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь")
    val calendar = Calendar.getInstance()
    calendar.time = Date(timestamp)
    return ruMonthNames[calendar.get(Calendar.MONTH)]
}