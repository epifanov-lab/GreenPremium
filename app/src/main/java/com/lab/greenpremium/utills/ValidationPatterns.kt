package com.lab.greenpremium.utills


import com.lab.greenpremium.R
import java.util.*
import java.util.regex.Pattern

enum class ValidationPatterns constructor(val pattern: String, val maxLength: Int, val errorTextResId: Int) {

    PHONE_RUS("^(\\+7|7|8)?[\\s\\-]?\\(?[489][0-9]{2}\\)?[\\s\\-]?[0-9]{3}[\\s\\-]?[0-9]{2}[\\s\\-]?[0-9]{2}$",
            18, R.string.error_wrong_phone),

    PHONE_OTHER("\\+1[\\d]{9,13}|\\+7[\\d]{10}|\\+[2345689][\\d]{9,13}", 14, R.string.error_wrong_phone),

    EMAIL("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])",
            66, R.string.error_wrong_email),


    PASSWORD("^[a-zA-Z0-9_\\.\\-@#\\$]{4,20}$", 20, R.string.error_wrong_pass),

    DOUBLE("[\\x00-\\x20]*[+-]?(NaN|Infinity|((((\\p{Digit}+)(\\.)?((\\p{Digit}+)?)" +
            "([eE][+-]?(\\p{Digit}+))?)|(\\.((\\p{Digit}+))([eE][+-]?(\\p{Digit}+))?)|" +
            "(((0[xX](\\p{XDigit}+)(\\.)?)|(0[xX](\\p{XDigit}+)?(\\.)(\\p{XDigit}+)))" +
            "[pP][+-]?(\\p{Digit}+)))[fFdD]?))[\\x00-\\x20]*",
            20, R.string.error_wrong_double),

    EMPTY_PATTERN(".*", 10, R.string.error_wrong_data);

    fun validate(s: String): Boolean {
        val pattern = Pattern.compile(this.pattern)
        val matcher = pattern.matcher(s)
        return matcher.matches()
    }
    companion object {
        fun asList(): List<ValidationPatterns> {
            return ArrayList(EnumSet.allOf(ValidationPatterns::class.java))
        }

        fun getType(s: String): ValidationPatterns? {
            for (p in asList()) {
                if (Pattern.compile(p.pattern, Pattern.CASE_INSENSITIVE).matcher(s).matches()) {
                    return p
                }
            }

            return null
        }
    }
}
