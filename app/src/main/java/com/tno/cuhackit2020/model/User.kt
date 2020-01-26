package com.tno.cuhackit2020.model

data class User(
    val userid: Int,
    val firstname: String,
    val lastname: String,
    val title: String,
    val dob: String,
    val state: String,
    val city: String,
    val verifiable: Int,
    val groupmembers: List<Group>
) {
    val fullName: String get() = "$firstname $lastname"
}