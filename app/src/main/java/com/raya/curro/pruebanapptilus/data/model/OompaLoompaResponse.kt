package com.raya.curro.pruebanapptilus.data.model

import com.google.gson.annotations.SerializedName

data class OompaLoompaResponse (
    @SerializedName("current") val current : Int? = null,
    @SerializedName("total") val total : Int? = null,
    @SerializedName("results") val results : ArrayList<Results> = arrayListOf()
)