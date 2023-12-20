package com.codeshinobi.zochitika.models

import kotlinx.serialization.Serializable

@Serializable
data class Chochitika(
    val address:String? = "",
    val coordinates:String? = "",
    val date:String? = "",
    val description:String? = "",
    val end_date:String? = "",
    val end_time:String? = "",
    val entry_fee:String? = "",
    val id:Int? = 0,
    val location:String? = "",
    val organiser:String? = "",
    val time:String? = "",
    val title:String? = "",
    val type:String? = ""
)
