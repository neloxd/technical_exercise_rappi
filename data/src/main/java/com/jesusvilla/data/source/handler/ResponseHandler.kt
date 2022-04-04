package com.jesusvilla.data.source.handler

import java.lang.RuntimeException
import java.net.SocketTimeoutException


/**
 * Created by Jesús Villa on 3/04/22
 */

enum class ErrorCodes(val code: Int) {
    SocketTimeOut(-1)

}

open class ResponseHandler {
    fun <T : Any> handleSuccess(data: T): Resource<T> {
        return Resource.success(data)
    }

    fun <T : Any> handleException(e: Exception): Resource<T> {
        return when (e) {
            is RuntimeException -> Resource.error(getErrorMessage(500), null)
            is SocketTimeoutException -> Resource.error(getErrorMessage(ErrorCodes.SocketTimeOut.code), null)
            else -> Resource.error(getErrorMessage(Int.MAX_VALUE), null)
        }
    }

    private fun getErrorMessage(code: Int): String {
        return when (code) {
            ErrorCodes.SocketTimeOut.code -> "Timeout"
            401 -> "Unauthorised"
            404 -> "Not found"
            else -> "Something went wrong, verify connection"
        }
    }
}