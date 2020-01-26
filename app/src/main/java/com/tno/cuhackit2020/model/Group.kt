package com.tno.cuhackit2020.model

data class Group(
    val groupid: Int,
    val name: String,
    val created: Long,
    val area: String,
    val description: String,
    val dateCreated: String,
    val groupMembers: List<User>
)