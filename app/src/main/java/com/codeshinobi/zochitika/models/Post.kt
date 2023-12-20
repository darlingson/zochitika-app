package com.codeshinobi.zochitika.models

import kotlinx.serialization.Serializable

@Serializable
data class Post(
    val id: Int? = null,
    val title: String? = null,
    val body: String? = null,
    val userId: Int? = null
)
