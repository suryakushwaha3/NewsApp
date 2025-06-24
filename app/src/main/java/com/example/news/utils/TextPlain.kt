package com.example.news.utils

import android.text.Html
import android.text.Spanned

fun String.toPlainText(): String {
    // Use Html.fromHtml() to convert HTML to Spanned and then extract plain text
    val spanned: Spanned = Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
    return spanned.toString()
}
