package xvar.okhttp.ext

import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.net.URLEncoder

//region const
const val EMPTY_STRING = ""
val JSON_MEDIA_TYPE = "application/json; charset=utf-8".toMediaType()
//end region

//region string
fun String?.utf8() : String? {
    return if (this == null)
        null
    else
        URLEncoder.encode(this, "utf-8")
}
fun Map<String, String>.toUrlEncodedStr() = this.map { (k, v) -> "${k.utf8()}=${v.utf8()}" }
    .joinToString(separator = "&")

inline fun String.toUrl(
    builderParams: HttpUrl.Builder.() -> HttpUrl.Builder = { this }
): HttpUrl {
    return this.toHttpUrl()
        .newBuilder()
        .builderParams()
        .build()
}

fun String.toUrl(
    queryParams: Map<String, String>,
    rewriteParams: Boolean = false
): HttpUrl {
    val httpUrl = this.toHttpUrl()
    val params = httpUrl.queryParameterNames
    return httpUrl
        .newBuilder().apply {
            queryParams.forEach { (key, value) ->
                if (rewriteParams || !params.contains(key)) {
                    this.addEncodedQueryParameter(key, value)
                }
            }
        }
        .build()
}
//endregion

//region rest
fun HttpUrl.get(
    builderParams: Request.Builder.() -> Request.Builder = { this }
): Request {
    return Request.Builder()
        .get()
        .url(this)
        .builderParams()
        .build()
}

fun HttpUrl.post(
    content: String = EMPTY_STRING,
    mediaType: MediaType = JSON_MEDIA_TYPE,
    builderParams: Request.Builder.() -> Request.Builder = { this }
): Request {
    return Request.Builder()
        .url(this)
        .post(content.toRequestBody(mediaType))
        .builderParams()
        .build()
}

fun HttpUrl.post(
    body: RequestBody,
    builderParams: Request.Builder.() -> Request.Builder = { this }
): Request {
    return Request.Builder()
        .url(this)
        .post(body)
        .builderParams()
        .build()
}

fun HttpUrl.put(
    content: String = EMPTY_STRING,
    mediaType: MediaType = JSON_MEDIA_TYPE,
    builderParams: Request.Builder.() -> Request.Builder = { this }
): Request {
    return Request.Builder()
        .url(this)
        .put(content.toRequestBody(mediaType))
        .builderParams()
        .build()
}

fun HttpUrl.put(
    body: RequestBody,
    builderParams: Request.Builder.() -> Request.Builder = { this }
): Request {
    return Request.Builder()
        .url(this)
        .put(body)
        .builderParams()
        .build()
}

fun HttpUrl.patch(
    content: String = EMPTY_STRING,
    mediaType: MediaType = JSON_MEDIA_TYPE,
    builderParams: Request.Builder.() -> Request.Builder = { this }
): Request {
    return Request.Builder()
        .url(this)
        .patch(content.toRequestBody(mediaType))
        .builderParams()
        .build()
}

fun HttpUrl.patch(
    body: RequestBody,
    builderParams: Request.Builder.() -> Request.Builder = { this }
): Request {
    return Request.Builder()
        .url(this)
        .patch(body)
        .builderParams()
        .build()
}

fun HttpUrl.delete(
    content: String = EMPTY_STRING,
    mediaType: MediaType = JSON_MEDIA_TYPE,
    builderParams: Request.Builder.() -> Request.Builder = { this }
): Request {
    return Request.Builder()
        .url(this)
        .delete(content.toRequestBody(mediaType))
        .builderParams()
        .build()
}

fun HttpUrl.delete(
    body: RequestBody,
    builderParams: Request.Builder.() -> Request.Builder = { this }
): Request {
    return Request.Builder()
        .url(this)
        .delete(body)
        .builderParams()
        .build()
}
//endregion