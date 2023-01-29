package com.raya.curro.pruebanapptilus.data.model

import com.google.gson.annotations.SerializedName

data class Favorite (
	@SerializedName("color") var color : String? = null,
	@SerializedName("food") var food : String? = null,
	@SerializedName("random_string") var randomString : String? = null,
	@SerializedName("song") var song : String? = null
)