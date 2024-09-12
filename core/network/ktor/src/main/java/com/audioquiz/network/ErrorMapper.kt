
package com.audioquiz.network

fun StatusCode.mapMessageStatusCode(): String {
    return when (this) {
        StatusCode.Unknown -> "Unknown"
        StatusCode.BadRequest -> "Bad Request"
        StatusCode.Forbidden -> "Forbidden"
        StatusCode.RequestTimeout -> "Request Timeout"
        StatusCode.TooManyRequests -> "Too many Requests"
        StatusCode.InternalServerError -> "Internal Server Error"
        StatusCode.ServiceUnavailable -> "Service Unavailable"
        StatusCode.AccessDenied -> "Access Denied"
        StatusCode.LimitRequest -> "Limit Request"
    }
}