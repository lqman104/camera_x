package com.luqman.android.camera.core.network.error

sealed class HttpError(
    override val message: String,
    val code: Int? = null,
    override val cause: Throwable? = null
) : Exception(message, cause) {
    class BadRequest(message: String) : HttpError(message, 400)
    class Unauthorized(message: String) : HttpError(message, 401)
    class NotFound(message: String) : HttpError(message, 404)
    class ServerError(message: String) : HttpError(message, 500)
    class Timeout(message: String) : HttpError(message)
    class NoInternet(message: String) : HttpError(message)
    class Unknown(message: String, code: Int? = null) : HttpError(message, code)
}
