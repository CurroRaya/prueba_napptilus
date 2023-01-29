package com.raya.curro.pruebanapptilus.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Results(
    @SerializedName("first_name") var firstName : String? = null,
    @SerializedName("last_name") var lastName : String? = null,
    @SerializedName("favorite") var favorite : Favorite? = Favorite(),
    @SerializedName("gender") var gender : String? = null,
    @SerializedName("image") var image : String?  = null,
    @SerializedName("profession") var profession : String? = null,
    @SerializedName("email") var email : String? = null,
    @SerializedName("age") var age : Int? = null,
    @SerializedName("country") var country : String? = null,
    @SerializedName("height") var height : Int? = null,
    @SerializedName("id") var id : Int? = null
): Serializable {
    fun getImageUrl(): String?{
        return image
    }
}