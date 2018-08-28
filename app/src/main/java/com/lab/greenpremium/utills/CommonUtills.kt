package com.lab.greenpremium.utills

import android.view.View
import android.view.ViewGroup
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols


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
 * @return locale specific currency string
 */
fun currencyFormat(amount: Double, groupingUsed: Boolean = true, decimals: Int = 2): String {
    val formatSymbols = DecimalFormatSymbols()
    formatSymbols.decimalSeparator = '.'
    formatSymbols.groupingSeparator = ' '
    val formatter = DecimalFormat()
    formatter.decimalFormatSymbols = formatSymbols

    //if (amount % 1 == 0) decimals = 0; // uncomment if want to remove zeroes

    formatter.minimumFractionDigits = decimals
    formatter.maximumFractionDigits = decimals
    formatter.isGroupingUsed = groupingUsed
    return formatter.format(amount) + " \u20BD"
}