package com.tno.cuhackit2020.model

data class Suggestion(
    val suggestionsid: Int,
    val description: String,
    val area: String,
    val user: Int,
    val date: String,
    val group: Int?,
    val likes: Int,
    val issue: Int,
    val parentSuggestion: Int? = null,
    var liked: Boolean? = null
)