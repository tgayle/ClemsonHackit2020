package com.tno.cuhackit2020.model

data class Issue(
    val issueid: Int,
    val date: String,
    val user: Int,
    val description: String,
    val area: String,
    val groupId: Int,
    val likes: Int,
    val title: String,
    val resolved: Int,
    val liked: Boolean? = null
)