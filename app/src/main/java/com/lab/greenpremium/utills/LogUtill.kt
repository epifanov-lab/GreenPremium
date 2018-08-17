package com.lab.greenpremium.utills

import android.util.Log
import java.text.MessageFormat
import java.util.*


/**
 * This class wraps [android.util.Log] and automatically adds to log message class name,
 * method name and line number at which log method was invoked. As a tag parameter it uses
 * [.name] field to allow filter logcat messages by its value.
 * Log output can be completely disabled by setting [.loggingEnabled] to false.
 *
 * Created by shirshov on 12.04.2017.
 */

object LogUtil {

    private val TAG = "LogUtil"
    private val MAX_LOG_ENTRY_SIZE = 3000
    private val CONTINUE_TAG = "[CONTINUE]"

    private val loggingEnabled = true

    /**
     * List of class names that could be between LogUtil entries in stackTrace. Only start of the
     * class names is needed.
     */
    private val systemClasses = arrayOf("java", "com.android")

    private val location: String
        get() {
            var found = false
            for (traceElement in Thread.currentThread().stackTrace) {
                found = found || isTraceElementPointsToLogUtil(traceElement)
                if (found && !isTraceElementPointsToLogUtil(traceElement) && !isTraceElementPointsToSystemClass(traceElement)) {
                    return MessageFormat.format("[{0}:{1}:{2}]: ",
                            parseClassName(traceElement),
                            traceElement.methodName,
                            traceElement.lineNumber)
                }
            }
            return "[]: "
        }

    /**
     * Wraps [android.util.Log.v] as stated in class description
     *
     * @param msg Message to be printed in log
     */
    fun v(msg: String) {
        if (loggingEnabled) {
            for (message in splitLongLogMessages(location + msg)) {
                Log.v(TAG, message)
            }
        }
    }

    /**
     * Wraps [android.util.Log.d] as stated in class description
     *
     * @param msg Message to be printed in log
     */
    fun d(msg: String) {
        if (loggingEnabled) {
            for (message in splitLongLogMessages(location + msg)) {
                Log.d(TAG, message)
            }
        }
    }

    /**
     * Wraps [android.util.Log.i] as stated in class description
     *
     * @param msg Message to be printed in log
     */
    fun i(msg: String) {
        if (loggingEnabled) {
            for (message in splitLongLogMessages(location + msg)) {
                Log.i(TAG, message)
            }
        }
    }

    /**
     * Wraps [android.util.Log.w] as stated in class description
     *
     * @param msg Message to be printed in log
     */
    fun w(msg: String) {
        if (loggingEnabled) {
            for (message in splitLongLogMessages(location + msg)) {
                Log.w(TAG, message)
            }
        }
    }

    /**
     * Wraps [android.util.Log.e] as stated in class description
     *
     * @param msg Message to be printed in log
     */
    fun e(msg: String) {
        if (loggingEnabled) {
            for (message in splitLongLogMessages(location + msg)) {
                Log.e(TAG, message)
            }
        }
    }

    /**
     * Wraps [android.util.Log.wtf] as stated in class description
     *
     * @param msg Message to be printed in log
     */
    fun wtf(msg: String) {
        if (loggingEnabled) {
            for (message in splitLongLogMessages(location + msg)) {
                Log.wtf(TAG, message)
            }
        }
    }

    /**
     * Wraps [android.util.Log.v] as stated in class description
     *
     * @param msg Message to be printed in log
     */
    fun v(msg: String, e: Throwable) {
        if (loggingEnabled) {
            val messageParts = splitLongLogMessages(location + msg)
            val iterator = messageParts.iterator()
            while (iterator.hasNext()) {
                val message = iterator.next()
                if (iterator.hasNext()) {
                    Log.v(TAG, message)
                } else {
                    Log.v(TAG, message, e)
                }
            }
        }
    }

    /**
     * Wraps [android.util.Log.d] as stated in class description
     *
     * @param msg Message to be printed in log
     */
    fun d(msg: String, e: Throwable) {
        if (loggingEnabled) {
            val messageParts = splitLongLogMessages(location + msg)
            val iterator = messageParts.iterator()
            while (iterator.hasNext()) {
                val message = iterator.next()
                if (iterator.hasNext()) {
                    Log.d(TAG, message)
                } else {
                    Log.d(TAG, message, e)
                }
            }
        }
    }

    /**
     * Wraps [android.util.Log.i] as stated in class description
     *
     * @param msg Message to be printed in log
     */
    fun i(msg: String, e: Throwable) {
        if (loggingEnabled) {
            val messageParts = splitLongLogMessages(location + msg)
            val iterator = messageParts.iterator()
            while (iterator.hasNext()) {
                val message = iterator.next()
                if (iterator.hasNext()) {
                    Log.i(TAG, message)
                } else {
                    Log.i(TAG, message, e)
                }
            }
        }
    }

    /**
     * Wraps [android.util.Log.w] as stated in class description
     *
     * @param msg Message to be printed in log
     */
    fun w(msg: String, e: Throwable) {
        if (loggingEnabled) {
            val messageParts = splitLongLogMessages(location + msg)
            val iterator = messageParts.iterator()
            while (iterator.hasNext()) {
                val message = iterator.next()
                if (iterator.hasNext()) {
                    Log.w(TAG, message)
                } else {
                    Log.w(TAG, message, e)
                }
            }
        }
    }

    /**
     * Wraps [android.util.Log.e] as stated in class description
     *
     * @param msg Message to be printed in log
     */
    fun e(msg: String, e: Throwable) {
        if (loggingEnabled) {
            val messageParts = splitLongLogMessages(location + msg)
            val iterator = messageParts.iterator()
            while (iterator.hasNext()) {
                val message = iterator.next()
                if (iterator.hasNext()) {
                    Log.e(TAG, message)
                } else {
                    Log.e(TAG, message, e)
                }
            }
        }
    }

    /**
     * Wraps [android.util.Log.wtf] as stated in class description
     *
     * @param msg Message to be printed in log
     */
    fun wtf(msg: String, e: Throwable) {
        if (loggingEnabled) {
            val messageParts = splitLongLogMessages(location + msg)
            val iterator = messageParts.iterator()
            while (iterator.hasNext()) {
                val message = iterator.next()
                if (iterator.hasNext()) {
                    Log.wtf(TAG, message)
                } else {
                    Log.wtf(TAG, message, e)
                }
            }
        }
    }

    private fun parseClassName(traceElement: StackTraceElement): String {
        var className = traceElement.className
        val endIndex = className.indexOf('$')
        if (endIndex > 0) {
            className = className.substring(0, endIndex)
        }
        return className.substring(className.lastIndexOf('.') + 1, className.length)
    }

    private fun isTraceElementPointsToLogUtil(trace: StackTraceElement): Boolean {
        return trace.className.startsWith(LogUtil::class.java.name)
    }

    private fun isTraceElementPointsToSystemClass(trace: StackTraceElement): Boolean {
        var result = false
        val traceClassName = trace.className
        for (systemClassName in systemClasses) {
            result = traceClassName.startsWith(systemClassName)
            if (result) {
                break
            }
        }
        return result
    }

    private fun splitLongLogMessages(logMessage: String): List<String> {
        var logMessage = logMessage
        val splittedMessages = ArrayList<String>()
        while (true) {
            if (logMessage.length > MAX_LOG_ENTRY_SIZE) {
                splittedMessages.add(logMessage.substring(0, MAX_LOG_ENTRY_SIZE))
                logMessage = CONTINUE_TAG + logMessage.substring(MAX_LOG_ENTRY_SIZE, logMessage.length)
            } else {
                splittedMessages.add(logMessage)
                return splittedMessages
            }
        }
    }
}
