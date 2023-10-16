package id.dayona.pokemonx.data.pokedetail

import com.google.gson.annotations.SerializedName

data class Item(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("url")
	val url: String? = null
)