package id.dayona.pokemonx.data.pokemon

import com.google.gson.annotations.SerializedName

data class ResultsItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("url")
	val url: String? = null
)