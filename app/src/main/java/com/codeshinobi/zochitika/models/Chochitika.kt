package com.codeshinobi.zochitika.models

import kotlinx.serialization.Serializable

@Serializable
data class Chochitika(
    val address:String? = null,
    val coordinates:String? = null,
    val date:String? = null,
    val description:String? = null,
    val end_date:String? = null,
    val end_time:String? = null,
    val entry_fee:String? = null,
    val id:Int? = 0,
    val location:String? = null,
    val organiser:String? = null,
    val time:String? = null,
    val title:String? = null,
    val type:String? = null
)
